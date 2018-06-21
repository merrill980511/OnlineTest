package com.merrill.onlineTest.web.files;

import com.merrill.onlineTest.dao.ITestPaperDAO;
import com.merrill.onlineTest.dao.impl.TestPaperDAOImpl;
import com.merrill.onlineTest.domain.Option;
import com.merrill.onlineTest.domain.Question;
import com.merrill.onlineTest.domain.TestPaper;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@WebServlet("/admin/download")
public class DownloadServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String testId = req.getParameter("testId");
        Long id = new Long(-1);
        if (StringUtils.isNotBlank(testId)) {
            id = new Long(testId);
        }
        ITestPaperDAO dao = new TestPaperDAOImpl();
        TestPaper testPaper = dao.getTestPaperById(id);

        String fileName = testPaper.getName() + ".xlsx";
        System.out.println(fileName);

        String path = super.getServletContext().getRealPath("/WEB-INF/download/" + fileName);

        //创建工作簿
        XSSFWorkbook workBook = new XSSFWorkbook();
        //创建工作表  工作表的名字叫helloWorld
        XSSFSheet sheet = workBook.createSheet("sheet1");

        List<Question> questions = testPaper.getQuestions();
//        int max = 0;
        for (int i = 0; i < questions.size(); i++) {
            Question question = questions.get(i);
            XSSFRow row = sheet.createRow(i);
            XSSFCell cell = row.createCell(0, CellType.STRING);
            cell.setCellValue(question.getStem());
            List<Option> options = question.getOptions();
            for (int j = 0; j < options.size(); j++) {
                Option option = options.get(j);
                XSSFCell c = row.createCell(2*j+1, CellType.STRING);
                c.setCellValue(option.getItem());
                c = row.createCell(2*(j+1), CellType.STRING);
                c.setCellValue(option.getContent());
            }
            cell = row.createCell(2*options.size()+1, CellType.STRING);
            cell.setCellValue(question.getAnswer());
//            max = max > options.size() ? max : options.size();
        }
//        for (int i = 0; i < max; i++) {
//            XSSFRow row = sheet.createRow(0);
//            XSSFCell cell = row.createCell(i, CellType.STRING);
//            char c = 'A';
//            cell.setCellValue(c++);
//        }

        OutputStream out = new FileOutputStream(new File(path));
        workBook.write(out);
        out.close();
//        workBook.write(new File(path));
        workBook.close();

        //从服务器中找到被下载资源的绝对路径
        path = super.getServletContext().getRealPath("/WEB-INF/download/" + fileName);
        System.out.println(path);

        fileName = java.net.URLEncoder.encode(fileName,"UTF-8");
        //设置下载文件的建议保存名称
        resp.addHeader("Content-Disposition", "attachment;filename=" + new String(fileName.getBytes("UTF-8"),"ISO-8859-1"));
        //告诉浏览器不要直接打开文件，而是弹出下载框，保存文件
        resp.setContentType("application/msexcel");

        Files.copy(Paths.get(path), resp.getOutputStream());

    }



}
