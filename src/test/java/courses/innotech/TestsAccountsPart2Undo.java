package courses.innotech;

import courses.innotech.accounts.Account;
import courses.innotech.accounts.AccountSave;
import courses.innotech.currencies.ECurrency;
import courses.innotech.exceptions.EmptyStackMessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;

public class TestsAccountsPart2Undo {
  @Test
  public void undoThrows() {

    Account account = new Account("Владимир");

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
    Assertions.assertThrows(EmptyStackMessException.class,
        () -> account.undo(),
        "Объект находится в первоначальном состоянии. Откат не возможен");
  }

  @Test
  public void undoNotThrows() throws IllegalAccessException, InvocationTargetException {

    String firstName = "Владимир";
    Account account = new Account("Владимир");

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

    System.out.println(account.getCurrencyCount().values().toArray().length);
    Assertions.assertAll("Проверка отката объекта в первоначальное состояние",
        () -> Assertions.assertEquals(account.getClientName(), firstName, "имя владельца не совпадает"),
        () -> Assertions.assertTrue(account.getCurrencyCount().values().toArray().length == 0, "количество записей в массиве валюта-количество не равно 0")
    );
  }
}
