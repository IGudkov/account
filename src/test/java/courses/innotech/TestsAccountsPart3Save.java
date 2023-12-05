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
  public void saveUndo() {

    Account account = new Account("Владимир");

    AccountSave accountSaveOriginal = new AccountSave(account.getClientName(), account.getCurrencyCount());

    account.setClientName("Александр");

    account.changeCurrencyCount(ECurrency.RUB, 1000);
    account.changeCurrencyCount(ECurrency.EUR, 2000);
    account.changeCurrencyCount(ECurrency.USD, 3000);
    account.changeCurrencyCount(ECurrency.CNY, 4000);

    account.changeCurrencyCount(ECurrency.RUB, 1001);
    account.changeCurrencyCount(ECurrency.EUR, 2001);
    account.changeCurrencyCount(ECurrency.USD, 3001);
    account.changeCurrencyCount(ECurrency.CNY, 4001);

    while (account.checkUndo()) {
      account.undo();
    }

    Account accountOriginal = new Account(accountSaveOriginal.getClientName(), accountSaveOriginal.getCurrencyCount());

    Assertions.assertAll("Проверка отката объекта в точку сохранения",
        () -> Assertions.assertEquals(account.getClientName(), accountOriginal.getClientName(), "имя владельца не совпадает"),
        () -> Assertions.assertArrayEquals(account.getCurrencyCount().values().toArray(), accountOriginal.getCurrencyCount().values().toArray(), "пары валюта-количество не совпадают")
    );
  }
}
