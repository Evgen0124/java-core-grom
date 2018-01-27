package lesson20.task2;

import lesson20.task2.exeption.BadRequestException;
import lesson20.task2.exeption.InternalServerException;
import lesson20.task2.exeption.LimitExceeded;

/**
 * Created by user on 01.12.2017.
 */
public class Controller {
    private TransactionDAO transactionDAO = new TransactionDAO();

    public Transaction save(Transaction transaction) throws LimitExceeded, BadRequestException,InternalServerException {
        return transactionDAO.save(transaction);

    }
    Transaction[] transactionList(){
        return  transactionDAO.transactionList();
    }
    Transaction[] transactionList(String city){
        return transactionDAO.transactionList(city);
    }
    Transaction[] transactionList(int amount){

        return transactionDAO.transactionList(amount);
    }
}
