package com.example.majorproject.Api;


import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiInterface
{

// Login & Registration
    @FormUrlEncoded
    @POST("User/register.php")
    Call<ResponseBody> performUserSignIn(@Field("name") String name,
                                         @Field("email") String email,
                                         @Field("password") String password,
                                         @Field("phoneNo") String phoneNo,
                                         @Field("profileImg") String profileImg,
                                         @Field("address") String address);

    @FormUrlEncoded
    @POST("User/login.php")
    Call<ResponseBody> performUserLogin(@Field("email") String email,
                                        @Field("password") String password);

    @FormUrlEncoded
    @POST("User/profileFetch.php")
    Call<ResponseBody> getProfile(@Field("userid") String userid);

    @Multipart
    @POST("User/updateProfile.php")
    Call<ResponseBody> updateprofile(@Part("userid") RequestBody  userid,
                                     @Part("name")  RequestBody  name,
                                     @Part("email") RequestBody  email,
                                     @Part("password") RequestBody  password,
                                     @Part("phoneNo") RequestBody  phoneNo,
                                     @Part MultipartBody.Part profileImg,
                                     @Part("address") RequestBody  address);

    @GET("User/getPopularProduct.php")
    Call<ResponseBody>getPopularProduct();

    @GET("User/getExploreProducts.php")
    Call<ResponseBody>getExploreProducts();

    @GET("User/getRecommendedProducts.php")
    Call<ResponseBody>getRecommendedProducts();

    @GET("User/getSlider.php")
    Call<ResponseBody>getSlider();

    @GET("User/getAllProducts.php")
    Call<ResponseBody>getAllproducts();

    @GET("User/getTotalProducts.php")
    Call<ResponseBody>getTotalProducts();

    @FormUrlEncoded
    @POST("User/addToCart.php")
    Call<ResponseBody> addtocart(@Field("userid") String userid,
                                 @Field("name") String name,
                                 @Field("description") String description,
                                 @Field("discount") String discount,
                                 @Field("type") String type,
                                 @Field("qty") int qty,
                                 @Field("price") int price,
                                 @Field("totalprice") int totalprice,
                                 @Field("rating") String rating,
                                 @Field("img_url") String img_url);
    @FormUrlEncoded
    @POST("User/getCartItem.php")
    Call<ResponseBody>getCartItem(@Field("userid") String userid);

    @FormUrlEncoded
    @POST("User/deleteCartItem.php")
    Call<ResponseBody>deleteCartItem(@Field("userid") String userid,
                                        @Field("id") String id);

    @FormUrlEncoded
    @POST("User/orderhistory.php")
    Call<ResponseBody> orderhistoryupdate(@Field("userid") String userid,
                                          @Field("username") String username,
                                          @Field("name") String name,
                                         @Field("date") String date,
                                         @Field("qty") String qty,
                                         @Field("price") String price,
                                         @Field("totalprice") int totalprice,
                                         @Field("rating") String rating,
                                          @Field("useraddress") String useraddress,
                                          @Field("userphoneno") String userphoneno,
                                          @Field("status") String status,
                                          @Field("useremail") String useremail,
                                         @Field("img_url") String img_url);



    @FormUrlEncoded
    @POST("User/getOrderHistory.php")
    Call<ResponseBody>getorderhistory(@Field("userid") String useri);

    @FormUrlEncoded
    @POST("User/search.php")
    Call<ResponseBody>search(@Field("name") String name);

    @GET("User/getOurVideos.php")
    Call<ResponseBody>getOurVideos();

    @FormUrlEncoded
    @POST("User/bookSoilService.php")
    Call<ResponseBody>bookSoilService(@Field("userid") String userid,
                                      @Field("name") String name,
                                      @Field("email") String email,
                                      @Field("phoneno") String phoneno,
                                      @Field("address") String address,
                                      @Field("plotaddress") String plotaddress,
                                      @Field("servicetype") String servicetype,
                                      @Field("date") String date,
                                      @Field("requesttype") String requesttype,
                                      @Field("status") String status);


    @GET("User/getHoneyproductlist.php")
    Call<ResponseBody>getHoneyproduct();

    @FormUrlEncoded
    @POST("User/bookHoneyService.php")
    Call<ResponseBody>bookHoneyService(@Field("userid") String userid,
                                        @Field("name") String name,
                                      @Field("email") String email,
                                      @Field("phoneno") String phoneno,
                                      @Field("address") String address,
                                       @Field("date") String date,
                                      @Field("message") String message,
                                       @Field("requesttype") String requesttype,
                                       @Field("status") String status);

    @GET("User/getGovScheme.php")
    Call<ResponseBody>getGovSchemes();

    @FormUrlEncoded
    @POST("User/bookGardenService.php")
    Call<ResponseBody>bookGardenService(@Field("userid") String userid,
                                        @Field("name") String name,
                                        @Field("email") String email,
                                        @Field("phoneno") String phoneno,
                                        @Field("address") String address,
                                        @Field("date") String date,
                                        @Field("message") String message,
                                        @Field("requesttype") String requesttype,
                                        @Field("status") String status);

    @GET("User/getGardenProjects.php")
    Call<ResponseBody>getGardenProjects();




}
