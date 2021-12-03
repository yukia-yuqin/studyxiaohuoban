package com.wangyuqin.mockito.lesson03;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;

public class DeepMockTest {
    public static File createCSVFile(List exportData, LinkedHashMap map,   String outPutPath, String fileName) {
        File csvFile = null;
        BufferedWriter csvFileOutputStream = null;
        try {
            File file = new File(outPutPath);
            if (!file.exists()) {
                file.mkdir();
            }
            // 定义文件名格式并创建
            csvFile = File.createTempFile(fileName, ".csv",
                    new File(outPutPath));
            // UTF-8使正确读取分隔符","
            csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(csvFile), "GBK"), 1024);
            // 写入文件头部
            for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator
                    .hasNext();) {
                java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator
                        .next();
                csvFileOutputStream
                        .write("" + (String) propertyEntry.getValue() != null ? (String) propertyEntry
                                .getValue() : "" + "");
                if (propertyIterator.hasNext()) {
                    csvFileOutputStream.write(",");
                }
            }
            csvFileOutputStream.newLine();
            // 写入文件内容
            for (Iterator iterator = exportData.iterator(); iterator.hasNext();) {
                Object row = (Object) iterator.next();
                for (Iterator propertyIterator = map.entrySet().iterator(); propertyIterator
                        .hasNext();) {
                    java.util.Map.Entry propertyEntry = (java.util.Map.Entry) propertyIterator
                            .next();
                    csvFileOutputStream.write((String) BeanUtils.getProperty(
                            row, (String) propertyEntry.getKey()));
                    if (propertyIterator.hasNext()) {
                        csvFileOutputStream.write(",");
                    }
                }
                if (iterator.hasNext()) {
                    csvFileOutputStream.newLine();
                }
            }
            csvFileOutputStream.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                csvFileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return csvFile;
    }








    public static void exportFile(HttpServletResponse response,
                                  String csvFilePath, String fileName) throws IOException {
//  response.setHeader("Content-type", "application-download");

        FileInputStream in = null;
        OutputStream out = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        response.setContentType("text/csv;charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment;filename="
                + URLEncoder.encode(fileName, "UTF-8"));
        response.setCharacterEncoding("UTF-8");
        try {
            in = new FileInputStream(csvFilePath);
            while ((len = in.read(buffer)) > 0) {
                out.write(buffer, 0, len);
            }
            out.flush();
        } catch (FileNotFoundException e) {
            LOG.error("获取文件错误!");
        } finally {
            if (in != null) {
                try {
                    in.close();
                    out.close();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    /**
     * 导出
     *
     * @param response
     * @param fileName
     * @return
     * @throws IOException
     */
    public static void downloadFile(HttpServletRequest request,
                                    HttpServletResponse response, String uri) throws IOException {
        // 获取服务其上的文件名称
        File file = new File(uri);
        String name = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date())
                + ".zip";
        StringBuffer sb = new StringBuffer();
        sb.append("attachment;  filename=").append(name);
        response.setHeader("Expires", "0");
        response.setHeader("Cache-Control",
                "must-revalidate, post-check=0, pre-check=0");
        response.setHeader("Pragma", "public");
        response.setContentType("application/x-msdownload;charset=UTF-8");
        response.setHeader("Content-Disposition", new String(sb.toString()
                .getBytes(), "ISO8859-1"));

        // 将此文件流写入到response输出流中
        FileInputStream inputStream = new FileInputStream(file);
        OutputStream outputStream = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int i = -1;
        while ((i = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, i);
        }
        outputStream.flush();
        outputStream.close();
        inputStream.close();
    }

    /**
     * 删除该目录filePath下的所有文件
     *
     * @param filePath
     *            文件目录路径
     */
    public static void deleteFiles(String filePath) {
        File file = new File(filePath);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    files[i].delete();
                }
            }
        }
    }

    /**
     * 删除单个文件
     *
     * @param filePath
     *            文件目录路径
     * @param fileName
     *            文件名称
     */
    public static void deleteFile(String filePath, String fileName) {
        File file = new File(filePath);
        if (file.exists()) {
            File[] files = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile()) {
                    if (files[i].getName().equals(fileName)) {
                        files[i].delete();
                        return;
                    }
                }
            }
        }
    }



    /**
     * 下载csv文件
     * @param path
     * @param response
     */
    private void download(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename="
                    + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(
                    response.getOutputStream());
            response.setContentType("application/vnd.ms-excel;charset=gb2312");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    private static final Log LOG = LogFactory.getLog(ExportCsvController.class);

    @RequestMapping(value = "/exportHistory")
    @SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
    public void exportHistory(HttpServletRequest request,
                              HttpServletResponse response, AccessDetailBean accessDetailBean)
            throws IOException {

        HttpSession session = request.getSession();
        UserBean userBean = (UserBean) session.getAttribute("userbean");

        if (!CommonUtil.isAdmin(userBean)) {
            if (userBean == null) {
                throw new RuntimeException("登陆失效，请重新登陆");
            }
            accessDetailBean.setUsername(userBean.getName());
        }
        LOG.debug("-----访问历史监控导出开始-----");
        String realPath = request.getRealPath("/WEB-INF/export"); // 获取绝对路径

        String path = realPath.replace("\\", "/");

        String fileName = "history_"; // 定义文件名称

        List exportData = new ArrayList<Map>();

        Map row = new LinkedHashMap<String, String>();

        LinkedHashMap map = new LinkedHashMap();
        map.put("1", "用户");
        map.put("2", "访问时间");
        map.put("3", "ip");
        map.put("4", "访问的服务");
        map.put("5", "消耗时间(毫秒)");
        map.put("6", "状态");
        map.put("7", "长度");

        int count = accessDetailExportService
                .queryHistoryCount(accessDetailBean);
        LOG.debug("访问历史监控总条数:" + count);

        if (count <= 100000) { // 如果查询总数大于10万条，不予导出
            // 查询访问历史记录
            List<AccessDetailBean> detailList = accessDetailExportService
                    .queryHistoryInfo(accessDetailBean);
            LOG.debug("-----访问历史监控数据条数-----" + detailList.size());
            for (AccessDetailBean detail : detailList) { // 执行导出操作
                row = new LinkedHashMap<String, String>();
                row.put("1", detail.getUsername()); // 用户名
                row.put("2", dateConvert(detail.getAccesstime())); // 访问时间
                row.put("3", detail.getClientip()); // 访问ip
                row.put("4", detail.getService()); // 访问的服务
                row.put("5", detail.getCosttime()); // 消耗时间
                row.put("6", statusConvert(detail.getStatus())); // 状态
                row.put("7", detail.getLength()); // 长度
                exportData.add(row);
            }
            File file = CsvUtils.createCSVFile(exportData, map, realPath,
                    fileName);
            String fileName2 = file.getName();
            LOG.debug("文件名称：" + fileName2);
            this.download(path + "/" + fileName2, response);
        } else { // 大于10万条不予导出
            CommonUtil.writeResult(response, "记录条数大于10万,导出失败,请添加条件过滤后重试!");
        }
        LOG.debug("-----访问历史监控导出结束-----");
    }

    @RequestMapping(value = "/exportSummary")
    @SuppressWarnings({ "rawtypes", "unchecked", "deprecation" })
    public void exportSummary(HttpServletRequest request,
                              HttpServletResponse response,
                              AccessDetailSummaryBean accessDetailSummaryBean) throws IOException {

        HttpSession session = request.getSession();
        UserBean userBean = (UserBean) session.getAttribute("userbean");

        if (!CommonUtil.isAdmin(userBean)) {
            if (userBean == null) {
                throw new RuntimeException("登陆失效，请重新登陆");
            }
        }

        LOG.debug("-----访问历史监控导出开始-----");
        String realPath = request.getRealPath("/WEB-INF/export"); // 获取绝对路径

        String path = realPath.replace("\\", "/");

        String fileName = "summary_"; // 定义文件名称

        List exportData = new ArrayList<Map>();

        Map row = new LinkedHashMap<String, String>();

        LinkedHashMap map = new LinkedHashMap();
        map.put("1", "访问者");
        map.put("2", "访问的服务");
        map.put("3", "ip");
        map.put("4", "访问key次数");
        map.put("5", "获取数据次数");
        map.put("6", "获取数据成功次数");
        map.put("7", "获取数据失败次数");
        map.put("8", "总次数");
        map.put("9", "数据大小");

        int count = accessDetailExportService
                .querySummaryCount(accessDetailSummaryBean);
        LOG.debug("访问历史统计记录总条数:" + count);

        if (count <= 100000) { // 如果查询总数大于10万条，不予导出
            // 查询访问历史记录
            List<AccessDetailSummaryBean> summaryList = accessDetailExportService
                    .querySummaryInfo(accessDetailSummaryBean);
            LOG.debug("-----访问历史监控数据条数-----" + summaryList.size());
            for (AccessDetailSummaryBean summary : summaryList) { // 执行导出操作
                row = new LinkedHashMap<String, String>();
                row.put("1", summary.getVisitor()); // 访问者
                row.put("2", summary.getVisitedServer()); // 访问的服务
                row.put("3", summary.getClientIp()); // ip
                row.put("4", summary.getVisitKeyTime()); // 访问key次数
                row.put("5", summary.getGainDataTime()); // 获取数据次数
                row.put("6", summary.getSuccessTime()); // 获取数据成功次数
                row.put("7", summary.getFailTime()); // 获取数据失败次数
                row.put("8", summary.getSumTime()); // 总次数
                row.put("9", summary.getDataVolume()); // 数据大小
                exportData.add(row);
            }
            File file = CsvUtils.createCSVFile(exportData, map, path, fileName);
            String fileName2 = file.getName();
            LOG.debug("文件名称：" + fileName2);
            this.download(path + "/" + fileName2, response);
        } else { // 大于10万条不予导出
            CommonUtil.writeResult(response, "记录条数大于10万,导出失败,请添加条件过滤后重试!");
        }
        LOG.debug("-----访问历史监控导出结束-----");
    }

    /**
     * 成功失败状态转换
     *
     * @param status
     * @return
     */
    public String statusConvert(Integer status) {
        String flag = null;
        if (status == 0) {
            flag = "成功";
        } else {
            flag = "失败";
        }
        return flag;
    }

    /**
     * 日期格式转换
     *
     * @param date
     * @return
     */
    public String dateConvert(long date) {
        // 设置日期格式
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 转为日期格式
        Date newDate = new Date(date);
        // 最终日期
        String finalDate = sdf.format(newDate);
        return finalDate;
    }

    /**
     * 下载csv文件
     * @param path
     * @param response
     */
    private void download(String path, HttpServletResponse response) {
        try {
            // path是指欲下载的文件的路径。
            File file = new File(path);
            // 取得文件名。
            String filename = file.getName();
            // 以流的形式下载文件。
            InputStream fis = new BufferedInputStream(new FileInputStream(path));
            byte[] buffer = new byte[fis.available()];
            fis.read(buffer);
            fis.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename="
                    + new String(filename.getBytes()));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream toClient = new BufferedOutputStream(
                    response.getOutputStream());
            response.setContentType("application/vnd.ms-excel;charset=gb2312");
            toClient.write(buffer);
            toClient.flush();
            toClient.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    /**导出访问历史记录*/
    function exportHistory() {
        var username = $('#username').val();
        var accesstimeStart=$('#accesstimeStart').datebox('getValue');
        var accesstimeEnd=$('#accesstimeEnd').datebox('getValue');
        var clientip = $('#clientip').val();
        var service = $('#service').val();
        var status = $('#status').val();
        var costtimeStart = $('#costtimeStart').val();
        var costtimeEnd = $('#costtimeEnd').val();
        window.location.href = $("#proPath").val() + "/export/exportHistory?username=" + username + "&accesstimeStart=" + accesstimeStart +
                "&accesstimeEnd=" + accesstimeEnd + "&clientip=" + clientip + "&service=" + service + "&costtimeStart=" +costtimeStart
                + "&costtimeEnd" + costtimeEnd;
    }

}
