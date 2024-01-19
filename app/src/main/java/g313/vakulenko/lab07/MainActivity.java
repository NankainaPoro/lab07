package g313.vakulenko.lab07;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txt_key;
    EditText txt_value;

    DB mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txt_key = findViewById(R.id.txt_key);
        txt_value = findViewById(R.id.txt_value);

        mydb = new DB( this, "mybase.db", null, 1);
    }



    public  void insertClick(View v)
    {
        String key = txt_key.getText().toString();
        String value = txt_value.getText().toString();

        mydb.do_insert(key, value);
    }

    public void updateClick(View v) {
        String key = txt_key.getText().toString();
        String newValue = txt_value.getText().toString();

        if (key.isEmpty() || newValue.isEmpty()) {
            // Показать уведомление, если поля пустые
            Toast.makeText(this, "Введите ключ и новое значение", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            mydb.do_update(key, newValue);
            // Добавить уведомление об успешном обновлении
            Toast.makeText(this, "Успешно обновлено", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            // Добавить уведомление об ошибке
            Toast.makeText(this, "Ошибка обновления", Toast.LENGTH_SHORT).show();
        }
    }

    public  void selectClick(View v)
    {
        String key = txt_key.getText().toString();
        String value = mydb.do_select(key);

        txt_value.setText(value);
    }

    public void deleteClick(View v) {
        String key = txt_key.getText().toString();

        if (key.isEmpty()) {
            Toast.makeText(this, "Введите ключ", Toast.LENGTH_SHORT).show();
            return;
        }

        // Проверка существования ключа в базе данных
        String value = mydb.do_select(key);
        if (value.equals("(!) not found")) {
            Toast.makeText(this, "Ключ не найден", Toast.LENGTH_SHORT).show();
            return;
        }

        // Диалоговое окно для подтверждения удаления
        new AlertDialog.Builder(this)
                .setTitle("Подтверждение удаления")
                .setMessage("Вы точно хотите удалить?")
                .setPositiveButton("Да", (dialog, which) -> {
                    // Удаление записи из базы данных
                    mydb.do_delete(key);

                    // Очистка полей ввода
                    txt_key.setText("");
                    txt_value.setText("");

                    // Уведомление об успешном удалении
                    Toast.makeText(this, "Удалено", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Нет", null)
                .show();
    }

}