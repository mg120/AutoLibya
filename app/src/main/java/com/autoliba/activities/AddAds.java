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
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.autoliba.NetworkTester;
import com.autoliba.PermissionUtils;
import com.autoliba.ProgressRequestBody;
import com.autoliba.R;
import com.autoliba.model.AddNewModel;
import com.autoliba.model.SettingInfoModel;
import com.autoliba.networking.ApiClient;
import com.autoliba.networking.ApiService;
import com.fourhcode.forhutils.FUtilsValidation;

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

public class AddAds extends AppCompatActivity implements ProgressRequestBody.UploadCallbacks, View.OnClickListener {

    TextView back;
    LinearLayout add_new_imgs_layout;
    ImageView new_image1, new_image2, new_image3, new_image4;
    EditText new_owner_ed, owner_phone_ed, owner_email_ed, car_price_ed, car_desc_ed;
    Button add_btn, add_other_images;
    SpotsDialog dialog;
    RadioGroup radioGroup, contact_group, type_radio_group;
    NetworkTester networkTester = new NetworkTester(this);
    Spinner categories_spinner, brand_spinner, modal_spinner, countries_spinner, areas_spinner, Ad_state_spinner;


    ArrayList<Uri> images = new ArrayList<>();
    MultipartBody.Part mPartMainImage, mPartImageTwo, mPartImageThree, mPartImageFour;
    List<MultipartBody.Part> parts = new ArrayList<>();

    private static final int INTENT_REQUEST_CODE = 123;
    ApiService apiService;
    int price_type, contact_type, used_type = 0;
    String selected_country, selected_area, selected_brand, selected_model, selected_state, selected_category;
    int selected_area_id, selected_country_id, selected_categotry_id, selected_brand_id, modal_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);

        getSettingInfo();

        back = findViewById(R.id.add_back_txt_id);
        add_new_imgs_layout = findViewById(R.id.add_new_imags_layout_id);
        new_image1 = findViewById(R.id.new_img1_id);
        new_image2 = findViewById(R.id.new_img2_id);
        new_image3 = findViewById(R.id.new_img3_id);
        new_image4 = findViewById(R.id.new_img4_id);
        new_owner_ed = findViewById(R.id.addNew_name_ed);
        owner_phone_ed = findViewById(R.id.addNew_phone_ed);

        categories_spinner = findViewById(R.id.categories_spinner);
        brand_spinner = findViewById(R.id.brands_spinner);
        modal_spinner = findViewById(R.id.models_spinner);
        countries_spinner = findViewById(R.id.countries_spinner);
        areas_spinner = findViewById(R.id.areas_spinner);
        Ad_state_spinner = findViewById(R.id.Ad_state_spinner);

        owner_email_ed = findViewById(R.id.addNew_email_ed);
        car_price_ed = findViewById(R.id.addNew_price_ed);
        add_btn = findViewById(R.id.addNew_add_btn_id);
        add_other_images = findViewById(R.id.add_images_btn);
        radioGroup = findViewById(R.id.radio_group);
        contact_group = findViewById(R.id.contact_radio_group);
        car_desc_ed = findViewById(R.id.addNew_desc_ed);

        radioGroup.check(R.id.negotation_btn);
        contact_group.check(R.id.yes_contact_btn);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        add_new_imgs_layout.setOnClickListener(new View.OnClickListener() {
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

        add_other_images.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPickImageWithPermission();
            }
        });

        add_btn.setOnClickListener(this);
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

                    for (int i = 0; i < counts_list.size(); i++) {
                        countries.add(counts_list.get(i).getName());
                    }
                    final ArrayAdapter<String> adapter = new ArrayAdapter<String>(AddAds.this, android.R.layout.simple_dropdown_item_1line, countries);
                    countries_spinner.setAdapter(adapter);
                    countries_spinner.setSelection(0);
                    getcountryArea(counts_list.get(0).getCity());

                    countries_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    for (int x = 0; x < modals_list.size(); x++)
                        models.add(modals_list.get(x).getModal());

                    final ArrayAdapter<String> models_adapter = new ArrayAdapter<String>(AddAds.this, android.R.layout.simple_dropdown_item_1line, models);
                    modal_spinner.setAdapter(models_adapter);
                    modal_spinner.setSelection(0);

                    modal_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
                    for (int x = 0; x < typeList.size(); x++)
                        categories.add(typeList.get(x).getName());

                    final ArrayAdapter<String> category_adapter = new ArrayAdapter<String>(AddAds.this, android.R.layout.simple_dropdown_item_1line, categories);
                    categories_spinner.setAdapter(category_adapter);
                    categories_spinner.setSelection(0);

                    categories_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selected_category = adapterView.getSelectedItem().toString();
                            selected_categotry_id = typeList.get(i).getId();
                            List<SettingInfoModel.City_> type_city_list = typeList.get(i).getCity();

                            getCategory_moedls(type_city_list);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });

                    //--------------- Ad state Adapter ------------
                    String[] states = {"جديد", "مستعمل"};
                    final ArrayAdapter<String> states_adapter = new ArrayAdapter<String>(AddAds.this, android.R.layout.simple_dropdown_item_1line, states);
                    Ad_state_spinner.setAdapter(states_adapter);
                    Ad_state_spinner.setSelection(0);

                    Ad_state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selected_state = adapterView.getSelectedItem().toString();
                            if (selected_state.equals("جديد")) {
                                used_type = 0;
                            } else if (selected_state.equals("مستعمل")) {
                                used_type = 1;
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

    private void getCategory_moedls(final List<SettingInfoModel.City_> type_city_list) {
        List<String> list = new ArrayList<>();
        for (int y = 0; y < type_city_list.size(); y++)
            list.add(type_city_list.get(y).getName());

        final ArrayAdapter<String> city_adapter = new ArrayAdapter<String>(AddAds.this, android.R.layout.simple_dropdown_item_1line, list);
        brand_spinner.setAdapter(city_adapter);
        brand_spinner.setSelection(0);

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
        for (int i = 0; i < city_list.size(); i++) {
            cities.add(city_list.get(i).getName());
        }
        final ArrayAdapter<String> city_adapter = new ArrayAdapter<String>(AddAds.this, android.R.layout.simple_dropdown_item_1line, cities);
        areas_spinner.setAdapter(city_adapter);
        areas_spinner.setSelection(0);

        areas_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == INTENT_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {

                try {
                    android.net.Uri selectedImage = data.getData();
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    new_image1.setImageBitmap(bitmap);
                    InputStream is = getContentResolver().openInputStream(data.getData());

                    createMainImageMultiPartFile(getBytes(is));

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void createMainImageMultiPartFile(byte[] imageBytes) {
        RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
        mPartMainImage = MultipartBody.Part.createFormData("image", "image.jpg", requestFile);
    }


    public void getPickImageWithPermission() {
        if (PermissionUtils.canMakeSmores(Build.VERSION_CODES.LOLLIPOP_MR1)) {
            if (!PermissionUtils.hasPermissions(AddAds.this, PermissionUtils.IMAGE_PERMISSIONS)) {
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
        TedBottomPicker bottomSheetDialogFragment = new TedBottomPicker.Builder(AddAds.this)
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

        new_image2.setImageURI(null);
        new_image3.setImageURI(null);
        new_image4.setImageURI(null);

        if (images.size() == 1) {
            new_image2.setImageURI(images.get(0));
            InputStream is = getContentResolver().openInputStream(images.get(0));
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), getBytes(is));
            mPartImageTwo = MultipartBody.Part.createFormData("items[]", "image.jpg", requestFile);

            parts.add(mPartImageTwo);

        } else if (images.size() == 2) {
            new_image2.setImageURI(images.get(0));
            InputStream is = getContentResolver().openInputStream(images.get(0));
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), getBytes(is));
            mPartImageTwo = MultipartBody.Part.createFormData("items[]", "image.jpg", requestFile);
//            createMainImageMultiPartFile(getBytes(is));
//            File ImageFile = new File(images.get(0).getPath());
//            final ProgressRequestBody fileBody = new ProgressRequestBody(ImageFile, this);
//            mPartImageOne = MultipartBody.Part.createFormData("itemimgs[]", ImageFile.getName(), fileBody);
            new_image3.setImageURI(images.get(1));
            InputStream is2 = getContentResolver().openInputStream(images.get(1));
            RequestBody requestFile2 = RequestBody.create(MediaType.parse("image/jpeg"), getBytes(is2));
            mPartImageThree = MultipartBody.Part.createFormData("items[]", "image.jpg", requestFile2);

            parts.add(mPartImageTwo);
            parts.add(mPartImageThree);

        } else if (images.size() == 3) {
            new_image2.setImageURI(images.get(0));
            InputStream is = getContentResolver().openInputStream(images.get(0));
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), getBytes(is));
            mPartImageTwo = MultipartBody.Part.createFormData("items[]", "image.jpg", requestFile);

            new_image3.setImageURI(images.get(1));
            InputStream is2 = getContentResolver().openInputStream(images.get(1));
            RequestBody requestFile2 = RequestBody.create(MediaType.parse("image/jpeg"), getBytes(is2));
            mPartImageThree = MultipartBody.Part.createFormData("items[]", "image.jpg", requestFile2);

            new_image4.setImageURI(images.get(2));
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
    public void onProgressUpdate(int percentage) {

    }

    @Override
    public void onError() {

    }

    @Override
    public void onFinish() {

    }


    @Override
    public void onClick(View view) {

        if (networkTester.isNetworkAvailable()) {
            // Validate Data ...
            if (!FUtilsValidation.isEmpty(new_owner_ed, getString(R.string.required))
                    && !FUtilsValidation.isEmpty(owner_email_ed, getString(R.string.required))
                    && FUtilsValidation.isValidEmail(owner_email_ed, getString(R.string.enter_valid_email))
                    && !FUtilsValidation.isEmpty(owner_phone_ed, getString(R.string.required))
                    && !FUtilsValidation.isEmpty(car_price_ed, getString(R.string.required))
                    && !FUtilsValidation.isEmpty(car_desc_ed, getString(R.string.required))
                    ) {
                RequestBody title_Part = RequestBody.create(MultipartBody.FORM, new_owner_ed.getText().toString().trim());
                RequestBody email_Part = RequestBody.create(MultipartBody.FORM, owner_email_ed.getText().toString().trim());
                RequestBody phone_Part = RequestBody.create(MultipartBody.FORM, owner_phone_ed.getText().toString().trim());
                RequestBody area_Part = RequestBody.create(MultipartBody.FORM, selected_area_id + "");
                RequestBody country_Part = RequestBody.create(MultipartBody.FORM, selected_country_id + "");
                RequestBody type_Part = RequestBody.create(MultipartBody.FORM, selected_categotry_id + "");
                RequestBody used_Part = RequestBody.create(MultipartBody.FORM, used_type + "");
                RequestBody model_Part = RequestBody.create(MultipartBody.FORM, selected_model);
                RequestBody price_Part = RequestBody.create(MultipartBody.FORM, car_price_ed.getText().toString().trim());
                RequestBody brand_Part = RequestBody.create(MultipartBody.FORM, selected_brand_id + "");
                RequestBody desc_Part = RequestBody.create(MultipartBody.FORM, car_desc_ed.getText().toString().trim());
                RequestBody negotation_Part = RequestBody.create(MultipartBody.FORM, price_type + "");
                RequestBody phoneContact_Part = RequestBody.create(MultipartBody.FORM, contact_type + "");
                RequestBody user_id_Part = RequestBody.create(MultipartBody.FORM, MainActivity.user_data.getId() + "");

                Log.e("user_id: ", MainActivity.user_data.getId() + "");
                Log.e("title: ", new_owner_ed.getText().toString().trim());

                Log.e("email: ", owner_email_ed.getText().toString().trim());
                Log.e("phone: ", owner_phone_ed.getText().toString().trim());
                Log.e("area: ", selected_area_id + "");
                Log.e("country: ", selected_country_id + "");
                Log.e("type: ", selected_categotry_id + "");
                Log.e("used_Part: ", used_type + "");

                Log.e("price : ", car_price_ed.getText().toString().trim());
                Log.e("model_Part: ", selected_model);
                Log.e("price_state: ", price_type + "");
                Log.e("desc_Part: ", car_desc_ed.getText().toString().trim());
                Log.e("selected_brand: ", selected_brand_id + "");
                Log.e("phoneContact_Part: ", contact_type + "");
                Log.e("negotation_Part: ", price_type + "");

                dialog = new SpotsDialog(AddAds.this, getString(R.string.Adding), R.style.Custom);
                dialog.show();

                apiService = ApiClient.getClient().create(ApiService.class);
                Call<AddNewModel> call = apiService.add_New(new_owner_ed.getText().toString().trim(), selected_categotry_id + "", used_type + "", selected_brand_id + "", selected_country_id + "", selected_area_id + "", modal_id + "", car_price_ed.getText().toString().trim(), owner_phone_ed.getText().toString().trim(),
                        owner_email_ed.getText().toString().trim(), car_desc_ed.getText().toString().trim(), price_type + "", contact_type + "", user_id_Part, mPartMainImage, parts);
                call.enqueue(new Callback<AddNewModel>() {
                    @Override
                    public void onResponse(Call<AddNewModel> call, Response<AddNewModel> response) {
                        dialog.dismiss();
                        AddNewModel addNewModel = response.body();
                        if (addNewModel.getSuccess()) {
                            Toasty.success(AddAds.this, "تم اضافة المنتج بنجاح", 1500).show();
                            finish();
                        } else {
                            Toasty.error(AddAds.this, "حدث خطأ ما, برجاء المحاولة مرة أخرى", 1500).show();
                        }

                    }

                    @Override
                    public void onFailure(Call<AddNewModel> call, Throwable t) {
                        dialog.dismiss();
                    }
                });
            }
        } else {
            Toasty.error(AddAds.this, getString(R.string.error_connection), 1500).show();
        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.negotation_btn:
                if (checked) {
                    // Pirates are the best
                    price_type = 1;
                    Toast.makeText(this, "" + 1, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.non_negotation_btn:
                if (checked) {
                    // Ninjas rule
                    price_type = 0;
                }
                break;
        }
    }

    public void onRadioContactClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();
        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.yes_contact_btn:
                if (checked) {
                    // Pirates are the best
                    contact_type = 1;
                    Toast.makeText(this, "" + 1, Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.no_contact_btn:
                if (checked) {
                    // Ninjas rule
                    contact_type = 0;
                }
                break;
        }
    }

    /**
     * Hides the soft keyboard
     */
    public void hideSoftKeyboard() {
        if (getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    /**
     * Shows the soft keyboard
     */
    public void showSoftKeyboard(View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        view.requestFocus();
        inputMethodManager.showSoftInput(view, 0);
    }

}
