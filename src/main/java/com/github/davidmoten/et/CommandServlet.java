package com.github.davidmoten.et;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * General purpose servlet. Doesn't really offer the richness of a formal REST
 * interface but is ok for something simple.
 * 
 * @author dxm
 * 
 */
public class CommandServlet extends HttpServlet {

	private static final String COMMAND_SAVE_REPORT = "saveReport";
	private static final Object COMMAND_GET_VERSION = "getVersion";

	private static final long serialVersionUID = 8026282588720357161L;

	private final Database db = new Database();

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String command = req.getParameter("command");
		if (COMMAND_SAVE_REPORT.equals(command))
			saveReport(req);
		else if (COMMAND_GET_VERSION.equals(command))
			getVersion(resp);
		else
			throw new RuntimeException("unknown command: " + command);
	}

	private void saveReport(HttpServletRequest req) {
		// TODO Auto-generated method stub

	}

	private void getVersion(HttpServletResponse resp) {
		try {
			resp.getWriter().print(System.getProperty("application.version"));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

	}

	/**
	 * Returns the {@link Date} from a date string in format yyyy-MM-dd-HH-mm.
	 * Date string is assumed to be in UTC time zone.
	 * 
	 * @param date
	 * @return
	 */
	private Date parseDate(String date) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm-Z");
		try {
			return sdf.parse(date + "-UTC");
		} catch (ParseException e) {
			throw new RuntimeException(e);
		}

	}

}
