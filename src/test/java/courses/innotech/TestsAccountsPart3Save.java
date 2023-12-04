package courses.innotech;

import courses.innotech.accounts.Account;
import courses.innotech.accounts.AccountSave;
import courses.innotech.currencies.ECurrency;
import courses.innotech.exceptions.EmptyStackMessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

public class TestsAccountsPart3Save {
  @Test
  public void saveUndo() throws IllegalAccessException, InvocationTargetException {

    Account account = new Account("Владимир");

    AccountSave accountSaveOriginal = new AccountSave(account.getClientName(), account.getCurrencyCount(), account.getStackUndo());
    Account accountOriginal = new Account(accountSaveOriginal.getClientName(), accountSaveOriginal.getCurrencyCount(), accountSaveOriginal.getStackUndo());

    account.setClientName("Александр");

    account.changeCurrencyCount(ECurrency.RUB, 1000);
    account.changeCurrencyCount(ECurrency.EUR, 2000);
    account.changeCurrencyCount(ECurrency.USD, 3000);
    account.changeCurrencyCount(ECurrency.CNY, 4000);

    AccountSave accountSave = new AccountSave(account.getClientName(), account.getCurrencyCount(), account.getStackUndo());

    account.changeCurrencyCount(ECurrency.RUB, 1001);
    account.changeCurrencyCount(ECurrency.EUR, 2001);
    account.changeCurrencyCount(ECurrency.USD, 3001);
    account.changeCurrencyCount(ECurrency.CNY, 4001);

    account = new Account(accountSave.getClientName(), accountSave.getCurrencyCount(), accountSave.getStackUndo());

    while (account.checkUndo()) {
      account.undo();
    }

    Account finalAccount = account;
    Assertions.assertAll("Проверка отката объекта в точку сохранения и дальнейшего отката в первоначальное состояние",
        () -> Assertions.assertEquals(finalAccount.getClientName(), accountOriginal.getClientName(), "имя владельца не совпадает"),
        () -> Assertions.assertArrayEquals(finalAccount.getCurrencyCount().values().toArray(), accountOriginal.getCurrencyCount().values().toArray(), "пары валюта-количество не совпадают")
    );
  }
}
