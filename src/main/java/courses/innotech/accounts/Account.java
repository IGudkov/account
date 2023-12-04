package courses.innotech.accounts;

import courses.innotech.currencies.ECurrency;
import courses.innotech.exceptions.EmptyStackMessException;
import lombok.Getter;
import lombok.ToString;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.EnumMap;
import java.util.Map;
import java.util.Stack;

@ToString
public class Account {
  @Getter
  private String clientName;
  private Map<ECurrency,Integer> currencyCount = new EnumMap<>(ECurrency.class);
  private Stack<KeyValue> stackUndo = new Stack<>();

  public Account(String clientName, Map<ECurrency, Integer> currencyCount, Stack<KeyValue> stackUndo) {
    this.clientName = clientName;
    if (!currencyCount.isEmpty())
      this.currencyCount = new EnumMap<>(currencyCount);
    if (!stackUndo.isEmpty())
      this.stackUndo = (Stack<KeyValue>)stackUndo.clone();
  }

  public Account(String clientName) {
    setClientName(clientName);
  }

  public Stack<KeyValue> getStackUndo() {
    return (Stack<KeyValue>)stackUndo.clone();
  }

  public void setClientName(String clientName) {
    if (clientName == null || clientName.isEmpty())
      throw new IllegalArgumentException("Имя не может быть null или пустым");
    if (!(this.clientName == null || this.clientName.isEmpty()))
      this.stackUndo.push(new KeyValue("undoClientName", this.clientName));
    this.clientName = clientName;
  }

  public void undoClientName(String clientName){
    this.clientName = clientName;
  }
  public Map<ECurrency,Integer> getCurrencyCount() {
    return Map.copyOf(currencyCount);
  }

  private void undoCurrencyCount(Map.Entry<ECurrency,Integer> mapEntry){
    if (mapEntry.getValue() < 0)
      this.currencyCount.remove(mapEntry.getKey());
    else
      this.currencyCount.put(mapEntry.getKey(),mapEntry.getValue());
  }

  public void changeCurrencyCount(ECurrency currency, Integer count) {
    if (count != null && count < 0)
      throw new IllegalArgumentException("Количество валюты не может быть отрицательным");
    this.stackUndo.push(
        new KeyValue(
            "undoCurrencyCount",
            (this.currencyCount.containsKey(currency)) ?
                Map.entry(currency, currencyCount.get(currency)) :
                Map.entry(currency, -1)
        )
    );
    if (count != null)
      this.currencyCount.put(currency,count);
    else
      this.currencyCount.remove(currency);
  }

  public void undo() throws IllegalAccessException, InvocationTargetException {

    if (this.stackUndo.empty())
      throw new EmptyStackMessException("Объект находится в первоначальном состоянии. Откат не возможен");

    KeyValue keyValue = this.stackUndo.pop();

    for (Method declaredMethod : this.getClass().getDeclaredMethods()) {
      if (declaredMethod.getName().equals(keyValue.key)) {
        declaredMethod.invoke(this, keyValue.value);
        return;
      }
    }
    throw new RuntimeException("Метод "+keyValue.key+"для отката изменений не найден");
  }
  public boolean checkUndo() {
    return !this.stackUndo.isEmpty();
  }
}
