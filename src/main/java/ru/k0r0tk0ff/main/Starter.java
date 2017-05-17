package ru.k0r0tk0ff.main;

import ru.k0r0tk0ff.service.Settings;
/**
 * Created by user on 5/15/2017.
 */
public class Starter {
    int n;
    String url;
    String login;
    String password;

    /**
     * Setter for property 'n'.
     *
     * @param n Value to set for property 'n'.
     */
    public void setN(int n) {
        this.n = n;
    }

    /**
     * Setter for property 'ip'.
     *
     * @param ip Value to set for property 'ip'.
     */
    public void setUrl(String ip) {
        this.url = ip;
    }

    /**
     * Setter for property 'login'.
     *
     * @param login Value to set for property 'login'.
     */
    public void setLogin(String login) {
        this.login = login;
    }

    /**
     * Setter for property 'password'.
     *
     * @param password Value to set for property 'password'.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Getter for property 'n'.
     *
     * @return Value for property 'n'.
     */
    public int getN() {
        return n;
    }

    /**
     * Getter for property 'ip'.
     *
     * @return Value for property 'ip'.
     */
    public String getIp() {
        return url;
    }

    /**
     * Getter for property 'login'.
     *
     * @return Value for property 'login'.
     */
    public String getLogin() {
        return login;
    }

    /**
     * Getter for property 'password'.
     *
     * @return Value for property 'password'.
     */
    public String getPassword() {
        return password;
    }

	void insertDataToDB() {
    DbInserter dbInserter = new DbInserter();

    dbInserter.dbConnectAndInsert(
            this.getIp(),
		    this.getLogin(),
		    this.getPassword(),
		    this.getN());
    dbInserter.closeConnection();
	}

	void getDataFromDbAndGenerateXmlFile() {

	}
}
