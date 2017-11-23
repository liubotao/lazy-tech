package com.whh.common.utils.db;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.*;
import java.util.ResourceBundle;

/**
 * JDBC连接工具类
 * @author huahui.wu
 */
public class JdbcUtil {
    private static final Log logger = LogFactory.getLog(JdbcUtil.class);
    private String url;
    private String classDriver;
    private String userName;
    private String password;
    private Connection conn = null;
    private PreparedStatement pst = null;
    private boolean hasBatch = false;

    public JdbcUtil() {
        this.conn = this.getConnectionNoPool();
    }

    private Connection getConnectionNoPool() {
        if (this.conn != null) {
            return this.conn;
        } else {
            Connection connection = null;

            try {
                ResourceBundle rbn = ResourceBundle.getBundle("generator");
                this.url = rbn.getString("generator.jdbc.url");
                this.classDriver = rbn.getString("generator.jdbc.driver");
                this.userName = rbn.getString("generator.jdbc.username");
                this.password = rbn.getString("generator.jdbc.password");
            } catch (Exception var5) {
                logger.error("generator.properties not found", var5);
            }

            try {
                Class.forName(this.classDriver);
                connection = DriverManager.getConnection(this.url, this.userName, this.password);
            } catch (ClassNotFoundException var3) {
                logger.error("jdbc class not found", var3);
            } catch (SQLException var4) {
                logger.error("", var4);
            }

            return connection;
        }
    }

    public ResultSet executeQuery(String sql) throws SQLException {
        logger.debug(sql);
        this.pst = this.conn.prepareStatement(sql, 1004, 1007);
        return this.pst.executeQuery();
    }

    public Object executeScalar(String sql) throws SQLException {
        ResultSet rs = this.executeQuery(sql);
        return rs.first() ? rs.getObject(1) : null;
    }

    public ResultSet executeQuery(String sql, Object[] objs) throws SQLException {
        logger.debug(sql);
        this.pst = this.conn.prepareStatement(sql, 1004, 1007);
        if (objs != null) {
            for (int i = 0; i < objs.length; ++i) {
                this.pst.setObject(i + 1, objs[i]);
            }
        }

        return this.pst.executeQuery();
    }

    public int executeUpdate(String sql) throws SQLException {
        logger.debug(sql);
        this.pst = this.conn.prepareStatement(sql);
        return this.pst.executeUpdate();
    }

    public int executeUpdate(String sql, Object[] objs) throws SQLException {
        logger.debug(sql);
        this.pst = this.conn.prepareStatement(sql);
        if (objs != null) {
            for (int i = 0; i < objs.length; ++i) {
                this.pst.setObject(i + 1, objs[i]);
            }
        }

        return this.pst.executeUpdate();
    }

    public void addBatch(String sql, Object[] objs) throws SQLException {
        logger.debug(sql);
        if (this.pst == null && !this.hasBatch) {
            this.pst = this.conn.prepareStatement(sql);
        }

        if (objs != null) {
            for (int i = 0; i < objs.length; ++i) {
                this.pst.setObject(i + 1, objs[i]);
            }
        }

        if (null != this.pst) {
            this.pst.addBatch();
            this.hasBatch = true;
        }

    }

    public int[] executeBatch() throws SQLException {
        if (!this.hasBatch) {
            throw new SQLException("Do not add batch");
        } else {
            this.hasBatch = false;
            return this.pst.executeBatch();
        }
    }

    public void close() {
        try {
            if (this.pst != null) {
                this.pst.close();
                this.pst = null;
            }
        } catch (Exception var3) {
            ;
        }

        try {
            if (this.conn != null) {
                this.conn.close();
                this.conn = null;
            }
        } catch (Exception var2) {
            ;
        }

    }

    public void beginTransaction() throws SQLException {
        this.conn.setAutoCommit(false);
    }

    public void commitTransaction() throws SQLException {
        this.conn.commit();
        this.conn.setAutoCommit(true);
    }

    public void rollbackTransaction() throws SQLException {
        this.conn.rollback();
    }
}
