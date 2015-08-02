package com.example.testandroidweb01;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.android.volley.AuthFailureError;
import com.android.volley.Request.Method;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button btnGO, btnJSON;
	private EditText username, password;
	private TextView showTv;
	boolean flag = false;
	private String result;
	private RequestQueue mRequest;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		mRequest = Volley.newRequestQueue(this);

		btnGO = (Button) findViewById(R.id.btn_go);
		btnJSON = (Button) findViewById(R.id.btn_json);
		username = (EditText) findViewById(R.id.username_et);
		password = (EditText) findViewById(R.id.password_et);
		showTv = (TextView) findViewById(R.id.show_tv);

		btnGO.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getRequest(MainActivity.this);
				// Toast.makeText(MainActivity.this, "button clicked",
				// 1000).show();
			}

		});
		btnJSON.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				getJSON(MainActivity.this);
				// Toast.makeText(MainActivity.this, "button clicked",
				// 1000).show();
			}

		});

	}

	protected void getJSON(Context mainActivity) {
		JsonObjectRequest json = new JsonObjectRequest(
				"http://m.weather.com.cn/data/101010100.html",null,
				new Response.Listener<JSONObject>() {

					@Override
					public void onResponse(JSONObject response) {
						showTv.setText(response.toString());
					}

				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", error.getMessage(),error);
					}
				});
		mRequest.add(json);
	}

	public void getRequest(Context content) {

		StringRequest stringquqest = new StringRequest(Method.POST,
				"http://192.168.1.102:8080/TestAndroidWeb/android.jsp",
				new Response.Listener<String>() {
					@Override
					public void onResponse(String response) {
						result = response.trim();
						if (result.equals("0")) {
							showTv.setText("登录成功");
						} else if (result.equals("2")) {
							showTv.setText("密码错误");
						} else if (result.equals("1")) {
							showTv.setText("用户名错误");
						}
						Log.d("=====qiao====", response);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError error) {
						// TODO Auto-generated method stub
						Log.e("TAG", error.getMessage(), error);
					}
				}) {
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {
				Map<String, String> map = new HashMap<String, String>();
				map.put("username", username.getText().toString());
				map.put("password", password.getText().toString());
				return map;
			}
		};
		mRequest.add(stringquqest);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
