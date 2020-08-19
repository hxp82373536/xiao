package com.hxp.xiao.Util;

import com.jcraft.jsch.*;

import java.io.*;
import java.util.Properties;
import java.util.Vector;

public class SimpleSFTPClient {
    private ChannelSftp channelSftp;
    private Session session;

    public void login() throws IOException {
        String username = "hxp";
        String password = "xiao";
        String host = "172.17.230.185";
        int port = 22;
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(username, host, port);
            if (password != null) {
                session.setPassword(password);
            }
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");

            session.setConfig(config);
            session.connect();

            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
        } catch (JSchException e) {
            e.printStackTrace();
        }
    }


    public void download(String directory, String downloadFile, File saveFile) throws SftpException, FileNotFoundException {
        if (directory != null && !"".equals(directory)) {
            channelSftp.cd(directory);
        }
        channelSftp.get(downloadFile, new FileOutputStream(saveFile));
    }

    public void upload(String directory, String uploadFile, File saveFile) throws SftpException, FileNotFoundException {
        if (directory != null && !"".equals(directory)) {
            if(!isDirExist(directory,channelSftp)){
                this.makeDir(directory,channelSftp);
            }
            channelSftp.cd(directory);
        }
        channelSftp.put(new FileInputStream(saveFile),uploadFile);
    }

    public void uploadFiles(String directory, String sourcedir) throws SftpException, FileNotFoundException {
        File sourceFile = new File(sourcedir);
        File[] files = sourceFile.listFiles();
        if(!isDirExist(directory,channelSftp)){
            this.makeDir(directory,channelSftp);
        }
        if (files != null || files.length >= 0) {
            for (File f : files) {
                String sourcepath = f.getAbsolutePath();
                String mkdir = directory + "/" + f.getName();
                if (f.isDirectory()) {
                    try {
                        if(!isDirExist(mkdir,channelSftp)){
                            this.makeDir(mkdir,channelSftp);
                        }
                        uploadFiles(mkdir,sourcepath);
                    } catch (Exception e) {
                        channelSftp.mkdir(mkdir);
                    }
                    uploadFiles(mkdir, sourcepath);
                } else {
                    channelSftp.put(sourcepath, mkdir);
                }
            }
        }
    }

    public void logout() {
        if (channelSftp != null) {
            if (channelSftp.isConnected()) {
                channelSftp.disconnect();
            }
        }
        if (session != null) {
            if (session.isConnected()) {
                session.disconnect();
            }
        }
    }

    public long getFileSize(String directory,String downloadFile) throws SftpException {
        if (directory != null && !"".equals(directory)) {
            channelSftp.cd(directory);
        }
        Vector list = channelSftp.ls(downloadFile);
        if (list.isEmpty()) {
            return 0;
        }else{
            ChannelSftp.LsEntry lsEntry = (ChannelSftp.LsEntry) list.firstElement();
            SftpATTRS attrs = lsEntry.getAttrs();
            return attrs.getSize();
        }
    }

    public static boolean isDirExist(String directory,ChannelSftp sftp) {
        boolean isDirExistFlag = false;
        try {
            SftpATTRS sftpATTRS = sftp.lstat(directory);
            isDirExistFlag = true;
            return sftpATTRS.isDir();
        } catch (Exception e) {
            if (e.getMessage().toLowerCase().equals("no such file")) {
                isDirExistFlag = false;
            }
        }
        return isDirExistFlag;
    }


    public void makeDir(String directory,ChannelSftp sftp) {
        try {
            String[] dirs = directory.split("/");
            String tempPath = "";
            for (String dir : dirs) {
                if (null == dir || "".equals(dir)){
                    continue;
                }
                tempPath += "/" + dir;
                if(!isDirExist(tempPath,channelSftp)){
                    sftp.mkdir(tempPath+"/");
                }
                sftp.chmod(Integer.parseInt("777", 8),tempPath);
            }
        } catch (SftpException e) {
            e.printStackTrace();
        }
    }
}
