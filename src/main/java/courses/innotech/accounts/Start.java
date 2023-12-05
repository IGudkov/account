package courses.innotech.accounts;


import courses.innotech.accounts.Account;
import courses.innotech.accounts.AccountSave;
import courses.innotech.currencies.ECurrency;

import java.lang.reflect.InvocationTargetException;

public class Start {
  public static void main(String[] args) {

    System.out.println("\n"+"Создание объекта:");
    Account account = new Account("Владимир");
    System.out.println(account);

    System.out.println("\n"+"Изменение объекта:");
    account.setClientName("Александр");
    System.out.println(account);

    account.changeCurrencyCount(ECurrency.RUB, 1000);
    System.out.println(account);
    account.changeCurrencyCount(ECurrency.EUR, 2000);
    System.out.println(account);
    account.changeCurrencyCount(ECurrency.USD, 3000);
    System.out.println(account);
    account.changeCurrencyCount(ECurrency.CNY, 4000);
    System.out.println(account);

    AccountSave accountSave = new AccountSave(account.getClientName(), account.getCurrencyCount());
    System.out.println("\n"+"Сохранение состояния объекта:");
    System.out.println(accountSave);

    System.out.println("\n"+"Изменение объекта:");
    account.changeCurrencyCount(ECurrency.RUB, 1001);
    System.out.println(account);
    account.changeCurrencyCount(ECurrency.EUR, 2001);
    System.out.println(account);
    account.changeCurrencyCount(ECurrency.USD, 3001);
    System.out.println(account);
    account.changeCurrencyCount(ECurrency.CNY, 4001);
    System.out.println(account);

    System.out.println("\n"+"Откат изменений объекта:");
    while (account.checkUndo()) {
      account.undo();
      System.out.println(account);
    }

    Account accountRestore = new Account(accountSave.getClientName(), accountSave.getCurrencyCount());
    System.out.println("\n"+"Восстановление состояния объекта:");
    System.out.println(accountRestore);
  }
}
