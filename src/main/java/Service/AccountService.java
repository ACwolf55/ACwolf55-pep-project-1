package Service;

import Model.Account;
import DAO.AccountDAO;

public class AccountService {
    private AccountDAO accountDAO;

    public AccountService() {
        this.accountDAO = new AccountDAO();
    }

    public Account login(String username, String password) {
        Account account = accountDAO.login(username, password);

        if (account != null) {
            return account;
        } else {
            return null;
        }
    }

    public Account register(String username, String password) {
       
        if (username == null || username.trim().isEmpty() || password.length() < 4) {
            return null; 
        }

        if (accountDAO.userExists(username)) {
            return null; 
        }
        return accountDAO.register(username, password);
    }

}