package Service;

import DAO.AccountDAO;
import Model.Account;

public class AccountService {
    public AccountDAO accountDAO;

    // Constructors
    public AccountService() {
        accountDAO = new AccountDAO();
    }
    public AccountService(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    // Add a new account
    public Account addAccount(Account account) {
        // Check if username does not exist
        if (this.accountDAO.getAccount(account) == null) {
            return this.accountDAO.insertAccount(account);
        }

        return null;
    }

    // Get an account
    public Account getAccount(Account account) {
        return this.accountDAO.getAccount(account);
    }

    // Get an account by id
    public Account getAccountById(int id) {
        return this.accountDAO.getAccountById(id);
    }
}
