package com.autoliba.activities;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.autoliba.NetworkTester;
import com.autoliba.PermissionUtils;
import com.autoliba.R;
import com.autoliba.model.ProfileDataModel;
import com.autoliba.model.SettingInfoModel;
import com.autoliba.model.UpdateAdModel;
import com.autoliba.networking.ApiClient;
import com.autoliba.networking.ApiService;
import com.fourhcode.forhutils.FUtilsValidation;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import dmax.dialog.SpotsDialog;
import es.dmoral.toasty.Toasty;
import gun0912.tedbottompicker.TedBottomPicker;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateAd extends AppCompatActivity implements View.OnClickListener {

    ProfileDataModel.Myad Ad_data;
    ImageView ad_image1, ad_image2, ad_image3, ad_image4;
    Button edit_ad_images_btn;
    EditText name_ed, phone_ed, email_ed, desc_ed, price_ed;
    RadioButton appear_phone_rb, not_appear_phone_rb, neogate_price, non_neogate_price;
    Spinner country_spinner, area_spinner, brand_spinner, model_spinner, car_type_spinner, ad_state_spinner;
    Button edit_ad_btn;
    LinearLayout add_main_ad_img;
    TextView back;
    SpotsDialog dialog;

    NetworkTester networkTester = new NetworkTester(this);
    ApiService apiService;

    String selected_country, selected_area, selected_brand, selected_model, selected_state, selected_category;
    int selected_area_id, selected_country_id, selected_categotry_id, selected_brand_id, modal_id, price_neogation, appear_phoneContact;
    int ad_state, price_type, contact_type = 0;

    ArrayList<Uri> images = new ArrayList<>();
    MultipartBody.Part mPartMainImage, mPartImageTwo, mPartImageThree, mPartImageFour;
    List<MultipartBody.Part> parts = new ArrayList<>();

    private static final int INTENT_REQUEST_CODE = 12355;

    // to set previous value to spinner selection
    int modal_pos, country_pos, type_pos, state_pos, brand_pos, city_pos;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_ad);

        if (getIntent().getExtras() != null) {
            Ad_data = getIntent().getExtras().getParcelable("Ad_data");
        }
        back = findViewById(R.id.updateAd_back_txt_id);
        add_main_ad_img = findViewById(R.id.add_img_layout_id);
        ad_image1 = findViewById(R.id.edit_ad_img1_id);
        ad_image2 = findViewById(R.id.edit_ad_img2_id);
        ad_image3 = findViewById(R.id.edit_ad_img3_id);
        ad_image4 = findViewById(R.id.edit_ad_img4_id);
        edit_ad_images_btn = findViewById(R.id.edit_ad_images_btn);

        name_ed = findViewById(R.id.edit_ad_name_ed);
        phone_ed = findViewById(R.id.edit_ad_phone_ed);
        email_ed = findViewById(R.id.edit_ad_email_ed);
        desc_ed = findViewById(R.id.edit_ad_desc_ed);
        price_ed = findViewById(R.id.edit_ad_price_ed);

        appear_phone_rb = findViewById(R.id.update_appear_phone_rb);
        not_appear_phone_rb = findViewById(R.id.update_not_appear_phone_rb);
        neogate_price = findViewById(R.id.update_negotation_price_btn);
        non_neogate_price = findViewById(R.id.update_non_negotation_price_btn);

        country_spinner = findViewById(R.id.edit_countries_spinner_id);
        area_spinner = findViewById(R.id.edit_areas_spinner_id);
        brand_spinner = findViewById(R.id.edit_brand_spinner_id);
        model_spinner = findViewById(R.id.edit_model_spinner_id);
        car_type_spinner = findViewById(R.id.edit_car_type_spinner);
        ad_state_spinner = findViewById(R.id.edit_ad_state_spinner_id);
        edit_ad_btn = findViewById(R.id.edit_ad_btn_id);

        getSettingInfo();

        // Set Data to Views.......
        Picasso.with(UpdateAd.this).load("http://libyagt.com/users/images/" + Ad_data.getImage()).into(ad_image1);
        name_ed.setText(Ad_data.getTitle());
        email_ed.setText(Ad_data.getEmail());
        phone_ed.setText(Ad_data.getPhone());
        price_ed.setText(Ad_data.getPrice());
        desc_ed.setText(Ad_data.getAdDesc());

        selected_categotry_id = Integer.parseInt(Ad_data.getType());
        price_neogation = Integer.parseInt(Ad_data.getNeogation());
        appear_phoneContact = Integer.parseInt(Ad_data.getPhonecontact());

        if (Ad_data.getNeogation().equals("0")) {
            non_neogate_price.setChecked(true);
            contact_type = 0;
        } else {
            neogate_price.setChecked(true);
            contact_type = 1;
        }
        if (Ad_data.getNeogation().equals("0")) {
            not_appear_phone_rb.setChecked(true);
            price_neogation = 0;
        } else {
            appear_phone_rb.setChecked(true);
            price_neogation = 1;
        }

        Log.e("neogation: ", Ad_data.getNeogation());
        Log.e("phoneContact: ", Ad_data.getNeogation());
        Log.e("city: ", Ad_data.getCity());
        Log.e("modal: ", Ad_data.getModal());
        Log.e("brand: ", Ad_data.getBrand());
//        Log.e("imgs: /", Ad_data.get());


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        add_main_ad_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                try {
                    startActivityForResult(intent, INTENT_REQUEST_CODE);
                } catch (ActivityNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

        edit_ad_btn.setOnClickListener(this);
        edit_ad_images_btn.setOnClickListener(this);
    }

    private void getSettingInfo() {
        apiService = ApiClient.getClient().create(ApiService.class);
        Call<SettingInfoModel> call = apiService.getSettingInfo();
        call.enqueue(new Callback<SettingInfoModel>() {
            @Override
            public void onResponse(Call<SettingInfoModel> call, Response<SettingInfoModel> response) {
                SettingInfoModel settingInfoModel = response.body();
                boolean success = settingInfoModel.getSuccess();
                SettingInfoModel.Message message = settingInfoModel.getMessage();

                if (success) {
                    final List<String> countries = new ArrayList<>();
                    final List<String> models = new ArrayList<>();
                    final List<String> categories = new ArrayList<>();

                    final List<SettingInfoModel.Modal> modals_list = message.getModals();
                    final List<SettingInfoModel.Count> counts_list = message.getCounts();
                    final List<SettingInfoModel.Type> typeList = message.getTypes();

                    selected_country_id = Integer.parseInt(Ad_data.getCountry());
                    for (int i = 0; i < counts_list.size(); i++) {
                        countries.add(counts_list.get(i).getName());
                        if (Ad_data.getCountry().equals(counts_list.get(i).getId())) {
                            country_pos = i;
                        }
                    }
                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(UpdateAd.this, android.R.layout.simple_dropdown_item_1line, countries);
                    country_spinner.setAdapter(adapter);
                    country_spinner.setSelection(country_pos);
                    getcountryArea(counts_list.get(country_pos).getCity());

                    country_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selected_country = adapterView.getSelectedItem().toString();
                            selected_country_id = counts_list.get(i).getId();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    //---------------- Modal Adapter ----------------
                    modal_id = Integer.parseInt(Ad_data.getModal());
                    for (int x = 0; x < modals_list.size(); x++) {
                        models.add(modals_list.get(x).getModal());
                        if (modal_id == modals_list.get(x).getId()) {
                            modal_pos = x;
                        }
                    }
                    final ArrayAdapter<String> models_adapter = new ArrayAdapter<String>(UpdateAd.this, android.R.layout.simple_dropdown_item_1line, models);
                    model_spinner.setAdapter(models_adapter);
                    model_spinner.setSelection(modal_pos);
                    model_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selected_model = adapterView.getSelectedItem().toString();
                            modal_id = modals_list.get(i).getId();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    //--------------- Categories Adapter ------------
                    for (int x = 0; x < typeList.size(); x++) {
                        categories.add(typeList.get(x).getName());
                        if (Ad_data.getType().equals(typeList.get(x).getId() + "")) {
                            type_pos = x;
                            Log.e("type:: ", type_pos + "");
                        }
                    }

                    final ArrayAdapter<String> category_adapter = new ArrayAdapter<String>(UpdateAd.this, android.R.layout.simple_dropdown_item_1line, categories);
                    car_type_spinner.setAdapter(category_adapter);
                    car_type_spinner.setSelection(type_pos);
                    car_type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selected_category = adapterView.getSelectedItem().toString();
                            selected_categotry_id = typeList.get(i).getId();
                            List<SettingInfoModel.City_> type_city_list = typeList.get(type_pos).getCity();

                            getTypeBrands(type_city_list);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    //--------------- Ad state Adapter ------------
                    String[] states = {"جديد", "مستعمل"};
                    ad_state = Integer.parseInt(Ad_data.getUsed());
                    for (int xx = 0; xx < states.length; xx++) {
                        if (Ad_data.getSuspensed().equals(states[xx]))
                            state_pos = xx;

                    }
                    final ArrayAdapter<String> states_adapter = new ArrayAdapter<String>(UpdateAd.this, android.R.layout.simple_dropdown_item_1line, states);
                    ad_state_spinner.setAdapter(states_adapter);
                    ad_state_spinner.setSelection(state_pos);
                    ad_state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selected_state = adapterView.getSelectedItem().toString();
                            if (selected_state.equals("جديد")) {
                                ad_state = 0;
                            } else if (selected_state.equals("مستعمل")) {
                                ad_state = 1;
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<SettingInfoModel> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    private void getTypeBrands(final List<SettingInfoModel.City_> type_city_list) {
        List<String> list = new ArrayList<>();
        for (int y = 0; y < type_city_list.size(); y++) {
            list.add(type_city_list.get(y).getName());
            if (Ad_data.getBrand().equals(type_city_list.get(y).getId() + "")) {
                brand_pos = y;
            }
        }

        final ArrayAdapter<String> city_adapter = new ArrayAdapter<String>(UpdateAd.this, android.R.layout.simple_dropdown_item_1line, list);
        brand_spinner.setAdapter(city_adapter);
        brand_spinner.setSelection(brand_pos);
        brand_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_brand = adapterView.getSelectedItem().toString();
                selected_brand_id = type_city_list.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getcountryArea(final List<SettingInfoModel.City> city_list) {
        final List<String> cities = new ArrayList<>();
        selected_area_id = Integer.parseInt(Ad_data.getCity());
        Log.e("area_pos: ", selected_area_id + "");
        for (int i = 0; i < city_list.size(); i++) {
            cities.add(city_list.get(i).getName());
            if (Ad_data.getCity().equals(city_list.get(i).getId() + "")) {
                city_pos = i;
            }
        }
        final ArrayAdapter<String> city_adapter = new ArrayAdapter<String>(UpdateAd.this, android.R.layout.simple_dropdown_item_1line, cities);
        area_spinner.setAdapter(city_adapter);
        area_spinner.setSelection(city_pos);
        area_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                selected_area = adapterView.getSelectedItem().toString();
                selected_area_id = city_list.get(i).getId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    public void onClick(View view) {

        int id = view.getId();
        switch (id) {
            case R.id.edit_ad_btn_id:
                if (networkTester.isNetworkAvailable()) {
                    // Validate Data ...
                    if (!FUtilsValidation.isEmpty(name_ed, getString(R.string.required))
                            && !FUtilsValidation.isEmpty(email_ed, getString(R.string.required))
                            && FUtilsValidation.isValidEmail(email_ed, getString(R.string.enter_valid_email))
                            && !FUtilsValidation.isEmpty(phone_ed, getString(R.string.required))
                            && !FUtilsValidation.isEmpty(price_ed, getString(R.string.required))
                            && !FUtilsValidation.isEmpty(desc_ed, getString(R.string.required))
                            ) {

                        Log.e("user_id: ", MainActivity.user_data.getId() + "");
                        Log.e("ad_id: ", Ad_data.getId() + "");
                        Log.e("name: ", name_ed.getText().toString().trim());
                        Log.e("category: ", selected_categotry_id + "");
                        Log.e("adState: ", ad_state + "");
                        Log.e("brand: ", selected_brand_id + "");
                        Log.e("country: ", selected_country_id + "");
                        Log.e("area: ", selected_area_id + "");
                        Log.e("modal: ", modal_id + "");
                        Log.e("price: ", price_ed.getText().toString().trim());
                        Log.e("phone: ", phone_ed.getText().toString().trim());
                        Log.e("email: ", email_ed.getText().toString().trim());
                        Log.e("desc: ", desc_ed.getText().toString().trim());
                        Log.e("neogate: ", price_type + "");
                        Log.e("phoneContact: ", contact_type + "");

                        RequestBody userId_Part = RequestBody.create(MultipartBody.FORM, MainActivity.user_data.getId() + "");
                        RequestBody adId_Part = RequestBody.create(MultipartBody.FORM, Ad_data.getId() + "");
                        RequestBody name_Part = RequestBody.create(MultipartBody.FORM, name_ed.getText().toString().trim());
                        RequestBody category_Part = RequestBody.create(MultipartBody.FORM, selected_categotry_id + "");
                        RequestBody adState_Part = RequestBody.create(MultipartBody.FORM, ad_state + "");
                        RequestBody brand_Part = RequestBody.create(MultipartBody.FORM, selected_brand_id + "");
                        RequestBody country_Part = RequestBody.create(MultipartBody.FORM, selected_country_id + "");
                        RequestBody area_Part = RequestBody.create(MultipartBody.FORM, selected_area_id + "");
                        RequestBody modal_Part = RequestBody.create(MultipartBody.FORM, modal_id + "");
                        RequestBody price_Part = RequestBody.create(MultipartBody.FORM, price_ed.getText().toString().trim());
                        RequestBody phone_Part = RequestBody.create(MultipartBody.FORM, phone_ed.getText().toString().trim());
                        RequestBody email_Part = RequestBody.create(MultipartBody.FORM, email_ed.getText().toString().trim());
                        RequestBody desc_Part = RequestBody.create(MultipartBody.FORM, desc_ed.getText().toString().trim());
                        RequestBody neogation_Part = RequestBody.create(MultipartBody.FORM, price_neogation + "");
                        RequestBody phoneContact_Part = RequestBody.create(MultipartBody.FORM, appear_phoneContact + "");

                        dialog = new SpotsDialog(UpdateAd.this, getString(R.string.Updatting), R.style.Custom);
                        dialog.show();

                        apiService = ApiClient.getClient().create(ApiService.class);
                        Call<UpdateAdModel> call = apiService.Update_ad(userId_Part, adId_Part, name_Part, category_Part, adState_Part, brand_Part, country_Part, area_Part
                                , modal_Part, price_Part, phone_Part, email_Part, desc_Part, neogation_Part, phoneContact_Part, mPartMainImage, parts);
                        call.enqueue(new Callback<UpdateAdModel>() {
                            @Override
                            public void onResponse(Call<UpdateAdModel> call, Response<UpdateAdModel> response) {
                                dialog.dismiss();
                                UpdateAdModel updateAdModel = response.body();
                                if (updateAdModel.getSuccess()) {
                                    Toasty.success(UpdateAd.this, "تم تعديل المنتج بنجاح!", 1500).show();
                                    finish();
                                } else {
                                    Toasty.error(UpdateAd.this, "حدث خطأ ما, برجاء المحاولة مرة أخرى", 1500).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<UpdateAdModel> call, Throwable t) {
                                dialog.dismiss();
                                t.printStackTrace();
                            }
                        });
                    }
                } else {
                    Toasty.error(UpdateAd.this, getString(R.string.error_connection), 1500).show();
                }
                break;
            case R.id.edit_ad_images_btn:
                getPickImageWithPermission();
                break;
        }
    }

    public void getPickImageWithPermission() {
        if (PermissionUtils.canMakeSmores(Build.VERSION_CODES.LOLLIPOP_MR1)) {
            if (!PermissionUtils.hasPermissions(UpdateAd.this, PermissionUtils.IMAGE_PERMISSIONS)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(PermissionUtils.IMAGE_PERMISSIONS, 100);
                }
            } else {
                pickMultiImages();
            }
        } else {
            pickMultiImages();
        }
    }

    void pickMultiImages() {
        TedBottomPicker bottomSheetDialogFragment = new TedBottomPicker.Builder(UpdateAd.this)
                .setOnMultiImageSelectedListener(new TedBottomPicker.OnMultiImageSelectedListener() {
                    @Override
                    public void onImagesSelected(ArrayList<Uri> uriList) {
                        images.clear();
                        images.addAll(uriList);
                        if (images.size() > 0) {
                            try {
                                createMultiPartFile();
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                })
                .setTitle("اخترالصور")
                .setSelectMaxCount(3)
                .setSelectMinCount(1)
                .setPeekHeight(2600)
                .showTitle(false)
                .setCompleteButtonText(R.string.done)
                .create();
        bottomSheetDialogFragment.show(getSupportFragmentManager());
    }

    private void createMultiPartFile() throws IOException {

        ad_image2.setImageURI(null);
        ad_image3.setImageURI(null);
        ad_image4.setImageURI(null);

        if (images.size() == 1) {
            ad_image2.setImageURI(images.get(0));
            InputStream is = getContentResolver().openInputStream(images.get(0));
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), getBytes(is));
            mPartImageTwo = MultipartBody.Part.createFormData("items[]", "image.jpg", requestFile);

            parts.add(mPartImageTwo);

        } else if (images.size() == 2) {
            ad_image2.setImageURI(images.get(0));
            InputStream is = getContentResolver().openInputStream(images.get(0));
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), getBytes(is));
            mPartImageTwo = MultipartBody.Part.createFormData("items[]", "image.jpg", requestFile);
//            createMainImageMultiPartFile(getBytes(is));
//            File ImageFile = new File(images.get(0).getPath());
//            final ProgressRequestBody fileBody = new ProgressRequestBody(ImageFile, this);
//            mPartImageOne = MultipartBody.Part.createFormData("itemimgs[]", ImageFile.getName(), fileBody);
            ad_image3.setImageURI(images.get(1));
            InputStream is2 = getContentResolver().openInputStream(images.get(1));
            RequestBody requestFile2 = RequestBody.create(MediaType.parse("image/jpeg"), getBytes(is2));
            mPartImageThree = MultipartBody.Part.createFormData("items[]", "image.jpg", requestFile2);

            parts.add(mPartImageTwo);
            parts.add(mPartImageThree);

        } else if (images.size() == 3) {
            ad_image2.setImageURI(images.get(0));
            InputStream is = getContentResolver().openInputStream(images.get(0));
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), getBytes(is));
            mPartImageTwo = MultipartBody.Part.createFormData("items[]", "image.jpg", requestFile);

            ad_image3.setImageURI(images.get(1));
            InputStream is2 = getContentResolver().openInputStream(images.get(1));
            RequestBody requestFile2 = RequestBody.create(MediaType.parse("image/jpeg"), getBytes(is2));
            mPartImageThree = MultipartBody.Part.createFormData("items[]", "image.jpg", requestFile2);

            ad_image4.setImageURI(images.get(2));
            InputStream is3 = getContentResolver().openInputStream(images.get(2));
            RequestBody requestFile3 = RequestBody.create(MediaType.parse("image/jpeg"), getBytes(is3));
            mPartImageFour = MultipartBody.Part.createFormData("items[]", "image.jpg", requestFile3);

            parts.add(mPartImageTwo);
            parts.add(mPartImageThree);
            parts.add(mPartImageFour);
        }
    }

    public byte[] getBytes(InputStream is) throws IOException {
        ByteArrayOutputStream byteBuff = new ByteArrayOutputStream();
        int buffSize = 1024;
        byte[] buff = new byte[buffSize];

        int len = 0;
        while ((len = is.read(buff)) != -1) {
            byteBuff.write(buff, 0, len);
        }

        return byteBuff.toByteArray();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                try {
                    android.net.Uri selectedImage = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    ad_image1.setImageBitmap(bitmap);
                    InputStream is = getContentResolver().openInputStream(data.getData());

                    createMainImageMultiPartFile(getBytes(is));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void createMainImageMultiPartFile(byte[] bytes) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), bytes);
        mPartMainImage = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
    }

    public void onNeogationClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.update_negotation_price_btn:
                if (checked) {
                    // Pirates are the best
                    price_type = 1;
                    Toast.makeText(this, "" +price_type, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.update_non_negotation_price_btn:
                if (checked) {
                    // Ninjas rule
                    price_type = 0;
                    Toast.makeText(this, "" +price_type, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }

    public void PhoneContactChanged(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.update_appear_phone_rb:
                if (checked) {
                    // Pirates are the best
                    contact_type = 1;
                    Toast.makeText(this, "" +contact_type, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.update_not_appear_phone_rb:
                if (checked) {
                    // Ninjas rule
                    contact_type = 0;
                    Toast.makeText(this, "" +contact_type, Toast.LENGTH_SHORT).show();
                }
                break;
        }
    }
}
