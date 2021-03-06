package lesson22.task2;


import lesson22.task2.exeption.BadRequestException;
import lesson22.task2.exeption.InternalServerException;
import lesson22.task2.exeption.LimitExceeded;

import java.util.Calendar;
import java.util.Date;


/**
 * Created by user on 01.12.2017.
 */
public class TransactionDAO {
    private static Transaction[] transactions = new Transaction[10];
        private  static  Utils utils = new Utils();

    public  static  Transaction save(Transaction transaction) throws BadRequestException, InternalServerException {

        validate(transaction);

        for (int i= 0;i< transactions.length;i++){
            if (transactions[i] == null) {
                transactions[i] = transaction;
                return transactions[i];
            }
        }
        throw new InternalServerException("bad internalExeption " + transaction.getId());
    }

    private  static  void validate(Transaction transaction) throws BadRequestException, InternalServerException {

        if(transaction==null)
            throw new BadRequestException("transaction can't be null");

        if (transaction.getAmount() > utils.getLimitSimpleTransactionsPerAmount())
            throw new LimitExceeded("Transaction limit  exceed " + transaction.getId() + ". Can't be saved");

        long sum = 0;
        int count = 0;
        for (Transaction tr : getTransactionPerDay(transaction.getDateCreated())) {
            sum += tr.getAmount();
            count++;
        }

        if (sum + transaction.getAmount()> utils.getLimitTransactionsPerAmount())
            throw new LimitExceeded("Transaction limit per day amount exceed " + transaction.getId() + ". Can't be saved");

        if (count  >= utils.getLimitTransactionsPerDayCount())
            throw new LimitExceeded("Transaction limit per day count exceed " + transaction.getId() + ". Can't be saved");

        boolean flagCity = false;
        for (String city : utils.getCities()) {
            if (city == transaction.getCity())
                flagCity = true;
        }

        if (!flagCity)
            throw new BadRequestException("wrong city of transaction " + transaction.getId());

        boolean flagFreeSpace = false;
        for (Transaction tr : transactions)
            if (tr == null)
                flagFreeSpace = true;

        if (!flagFreeSpace)
            throw new InternalServerException("not enough free space to save transaction " + transaction.getId());

        for (Transaction tr : transactions)
            if (tr != null&&tr.equals(transaction))
                throw new BadRequestException("transaction allready exist id: " + transaction.getId());


    }
   public  static  Transaction[] transactionList(){       //список транзакций

        if (transactions == null)
            return  new Transaction[0];
        int count = 0;
        for (Transaction tr : transactions)
            if (tr != null &&tr.getAmount()>0)
                count++;

        if (count == 0)
            return  new Transaction[0];

        Transaction[] result = new Transaction[count];
        int index = 0;
        for (Transaction tr : transactions)
            if (tr != null&&tr.getAmount()>0) {
                result[index] = tr;
                index++;
            }
        return result;

    }
       public static  Transaction[] transactionList(String city){
        if (transactions == null)
            return new Transaction[0];

        int count = 0;
        for (Transaction tr : transactions)
            if (tr != null && tr.getCity().equals(city)&&tr.getAmount()>0)
                count++;

        if (count == 0)
            return new Transaction[0];

        Transaction[] result = new Transaction[count];
        int index = 0;
        for (Transaction tr : transactions)
            if (tr != null && tr.getCity().equals(city)&&tr.getAmount()>0) {
                result[index] = tr;
                index++;
            }
        return result;
    }
        public static  Transaction[] transactionList(int amount){
        if (transactions == null)
            return new Transaction[0];

        int count = 0;
        for (Transaction tr : transactions)
            if (tr != null && tr.getAmount() == amount)
                count++;

        if (count == 0)
            return new Transaction[0];

        Transaction[] result = new Transaction[count];
        int index = 0;
        for (Transaction tr : transactions)
            if (tr != null && tr.getAmount() == amount) {
                result[index] = tr;
                index++;
            }
        return result;
    }

    private  static  Transaction[] getTransactionPerDay(Date dateOfCurTransaction) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dateOfCurTransaction);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int count = 0;

        for (Transaction transaction : transactions) {
            if (transaction != null) {
                calendar.setTime(transaction.getDateCreated());
                int trMonth = calendar.get(Calendar.MONTH);
                int trDay = calendar.get(Calendar.DAY_OF_MONTH);

                if (trMonth == month && trDay == day)
                    count++;
            }
        }

        Transaction[] result = new Transaction[count];
        int index = 0;
        for (Transaction transaction : transactions) {
            if (transaction != null) {
                calendar.setTime(transaction.getDateCreated());
                int trMonth = calendar.get(Calendar.MONTH);
                int trDay = calendar.get(Calendar.DAY_OF_MONTH);

                if (trMonth == month && trDay == day) {
                    result[index] = transaction;
                    index++;
                }
            }
        }


        return result;
    }
}
