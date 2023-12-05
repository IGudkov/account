package courses.innotech.accounts;

import courses.innotech.currencies.ECurrency;
import courses.innotech.exceptions.EmptyStackMessException;
import lombok.Getter;
import lombok.ToString;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Currency;
import java.util.EnumMap;
import java.util.Map;
import java.util.Stack;

@ToString
public class Account {
  @Getter
  private String clientName;
  private Map<ECurrency,Integer> currencyCount = new EnumMap<>(ECurrency.class);
  private Stack<UndoActionInterface> stackUndo = new Stack<>();


  public Account(String clientName, Map<ECurrency, Integer> currencyCount) {
    this.clientName = clientName;
    if (!currencyCount.isEmpty())
      this.currencyCount = new EnumMap<>(currencyCount);
  }

  public Account(String clientName) {
    setClientName(clientName);
  }

  public void setClientName(String clientName) {
    if (clientName == null || clientName.isEmpty())
      throw new IllegalArgumentException("Имя не может быть null или пустым");
    if (!(this.clientName == null || this.clientName.isEmpty())) {
      String clientNameUndo = this.clientName;
      this.stackUndo.push(() -> {this.clientName = clientNameUndo;});
    }
    this.clientName = clientName;
  }

  public Map<ECurrency,Integer> getCurrencyCount() {
    return Map.copyOf(currencyCount);
  }

  public void changeCurrencyCount(ECurrency currency, Integer count) {
    if (count != null && count < 0)
      throw new IllegalArgumentException("Количество валюты не может быть отрицательным");

    if (this.currencyCount.containsKey(currency)) {
      Integer currencyUndo = this.currencyCount.get(currency);
      this.stackUndo.push(() -> {
        this.currencyCount.put(currency, currencyUndo);
      });
    } else {
      this.stackUndo.push(() -> {
        this.currencyCount.remove(currency);
      });
    }

    if (count != null)
      this.currencyCount.put(currency,count);
    else
      this.currencyCount.remove(currency);
  }

  public void undo() {

    if (this.stackUndo.empty())
      throw new EmptyStackMessException("Объект находится в первоначальном состоянии. Откат не возможен");

    UndoActionInterface undoActionInterface = this.stackUndo.pop();
    undoActionInterface.executeExpression();
  }
  public boolean checkUndo() {
    return !this.stackUndo.isEmpty();
  }
}
