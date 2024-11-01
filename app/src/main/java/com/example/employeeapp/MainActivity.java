package com.example.employeeapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private EmployeeApi employeeApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configurar Retrofit
        Retrofit retrofit = RetrofitClient.getClient();
        employeeApi = retrofit.create(EmployeeApi.class);

        // Fazer a chamada da API
        fetchEmployees();
    }

    private void fetchEmployees() {
        Log.d("MainActivity", "Iniciando a chamada da API...");
        Call<List<Employee>> call = employeeApi.getEmployees();
        call.enqueue(new Callback<List<Employee>>() {
            @Override
            public void onResponse(Call<List<Employee>> call, Response<List<Employee>> response) {
                if (response.isSuccessful()) {
                    List<Employee> employees = response.body();
                    // Processar a lista de funcionários
                    for (Employee employee : employees) {
                        Log.d("MainActivity", "Employee: " + employee.getName() + ", Role: " + employee.getRole());
                    }
                } else {
                    Toast.makeText(MainActivity.this, "Erro: " + response.code(), Toast.LENGTH_SHORT).show();
                    Log.e("MainActivity", "Erro na resposta: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<Employee>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Falha na requisição: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.e("MainActivity", "Erro: ", t);
            }
        });
    }

}
