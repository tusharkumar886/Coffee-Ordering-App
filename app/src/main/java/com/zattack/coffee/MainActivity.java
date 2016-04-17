package com.zattack.coffee;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int num = 0;
    int price = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void addOrder(View view) {    //add button method
        num++;
        price=num*5;
        display(num, price);
    }

    public void removeOrder(View view) { //remove button method
        if (num > 0 && price > 0) {
            num--;
            price=num*5;
        }
        display(num,price);
    }

    private void mailOrder(String summary) {   //method to display price of order
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_EMAIL, "tusharkumar_21@yahoo.com");
        intent.putExtra(Intent.EXTRA_SUBJECT, "Coffee Order");
        intent.putExtra(Intent.EXTRA_TEXT, summary);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    public void submitOrder(View view){
        EditText customerName = (EditText) findViewById(R.id.Name);
        String name= customerName.getText().toString();

        CheckBox chocolate = (CheckBox) findViewById(R.id.chocolate);
        boolean includeChocolate = chocolate.isChecked();

        CheckBox cinnamon = (CheckBox) findViewById(R.id.cinnamon);
        boolean includeCinnamon = cinnamon.isChecked();
        price=totalPrice(includeChocolate,includeCinnamon);
        String summary = orderSummary(name,num,price,includeChocolate,includeCinnamon);
        if(num==0 || price==0){
            Toast.makeText(this, "You must order something!", Toast.LENGTH_SHORT).show();
        }
        else
            mailOrder(summary);
    }
    public int totalPrice(boolean chocolate, boolean cinnamon){
        price=num*5;
        if(chocolate)
            price=num*6;
        if (cinnamon)
            price=num*7;
        if(chocolate&&cinnamon)
            price=num*8;
        return price;
    }
    private void display(int num,int price) {      //method to display quantity of order
        TextView quantity = (TextView) findViewById(R.id.quantity_no);
        quantity.setText("" + num);
        TextView priceTotal = (TextView) findViewById(R.id.amount);
        priceTotal.setText("$" + price);
    }

    public String orderSummary(String name,int num,int price, boolean addChocolate, boolean addCinnamon){
        String receipt ="Name: "+name;
        receipt+="\nQuantity: "+num;
        receipt+="\nAdd Chocolate ?: "+addChocolate;
        receipt+="\nAdd Cinnamon ?: "+addCinnamon;
        receipt+="\nTotal: $" + price;
        return receipt;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("Number", num);
        outState.putInt("Price",price);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        num = savedInstanceState.getInt("Number");
        price = savedInstanceState.getInt("Price");
        display(num,price);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
