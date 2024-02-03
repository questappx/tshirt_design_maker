package com.questappx.tshirtmaker;

import static com.questappx.tshirtmaker.Data.BG_ADAPTER;
import static com.questappx.tshirtmaker.Data.SHIRT_ADAPTER;
import static com.questappx.tshirtmaker.Data.bgShirtLink;
import static com.questappx.tshirtmaker.Data.shirtCategoryNames;
import static com.questappx.tshirtmaker.Data.shirtLink;
import static com.questappx.tshirtmaker.Data.stickerCategoryNames;
import static com.questappx.tshirtmaker.MainActivity.appOpenManager;
import static com.questappx.tshirtmaker.MainActivity.inApp;
import static com.questappx.tshirtmaker.MainActivity.interstitialAdImplement;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.Layout;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.facebook.ads.AdView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.questappx.tshirtmaker.AdsWorking.BannerAdImplement;
import com.questappx.tshirtmaker.AdsWorking.InterstitialAdImplement;
import com.questappx.tshirtmaker.Extras.CategoryAdapter;
import com.questappx.tshirtmaker.Extras.LongPressListener;
import com.questappx.tshirtmaker.Extras.SaveImage;
import com.questappx.tshirtmaker.SavedWorking.DeleteAlert;
import com.questappx.tshirtmaker.SavedWorking.SaveWork;
import com.xiaopo.flying.logoSticker.DrawableSticker;
import com.xiaopo.flying.logoSticker.Sticker;
import com.xiaopo.flying.logoSticker.StickerView;
import com.xiaopo.flying.logoSticker.TextSticker;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Random;

import yuku.ambilwarna.AmbilWarnaDialog;


public class EditorActivity extends AppCompatActivity {

    //Admob Rewarded
    RewardedAd mRewardedAd;


    private static final int LOAD_IMAGE_RESULTS = 1;


    public String TAG = "adderr";
    int activityOpenAd;
    int fbInterstitialReward;
   public static AdView fbAdView;
    Object drawableFrameBg;

    ArrayList<String> bgsLinks = new ArrayList<>();
    ArrayList<String> tshirtsLinks = new ArrayList<>();

    FrameAdapter shirtAdapter, bgsAdapter;
    int gradColor1, gradColor2;
    int photoFromGallery = Data.PHOTO_GALLERY_FOR_BG;

    public  int[] colorIDs = {R.drawable.color_picker,  R.color.white, R.color.black,R.color.grey, R.color.purple, R.color.purple_200, R.color.darkpurple, R.color.teal_200, R.color.teal_700, R.color.pink, R.color.blue, R.color.violet, R.color.darkviolet, R.color.darkblue, R.color.grey, R.color.lightgreen, R.color.darkgreen, R.color.yellow, R.color.lightyellow, R.color.lightorange, R.color.darkorange, R.color.brown, R.color.darkbrown , R.color.lightblue, R.color.lightgreen, R.color.parrotlight, R.color.trustviolet, R.color.purplelight, R.color.bluelight, R.color.redlight, R.color.violetdark};
    public  int[] fontsIDs = {R.font.minion_concept_font, R.font.font1,R.font.newfonts1,R.font.newfonts2,R.font.font3,R.font.newfonts3,R.font.font4,R.font.newfonts4,R.font.font5,R.font.newfonts5,R.font.font6,R.font.newfonts7,R.font.font8,R.font.newfonts8,R.font.font9,R.font.newfonts9,R.font.font10,R.font.newfonts10,R.font.font11,R.font.newfonts11,R.font.font12,R.font.newfonts12,R.font.font13,R.font.newfonts13,R.font.font14,R.font.newfonts14,R.font.font15,R.font.newfonts15,R.font.font16,R.font.newfonts16,R.font.font17,R.font.newfonts17,R.font.font18,R.font.newfonts18,R.font.font19,R.font.newfonts19,R.font.font20,R.font.newfonts20,R.font.font21,R.font.newfonts21,R.font.font22,R.font.newfonts22,R.font.font23,R.font.font24,R.font.newfonts24,R.font.font25,R.font.newfonts25,R.font.font26,R.font.newfonts26,R.font.font27,R.font.newfonts27,R.font.font28,R.font.newfonts28,R.font.font29,R.font.newfonts29,R.font.font30,R.font.font31,R.font.font32,R.font.font33,R.font.font34,R.font.font35,R.font.font36,R.font.font38,R.font.font39,R.font.montserrat, R.font.besbas};
    public  int[] filterIDs = {android.R.color.transparent,R.drawable.effects_1,R.drawable.effects_2,R.drawable.effects_3,R.drawable.effects_4,R.drawable.effects_7,R.drawable.effects_8,R.drawable.effects_9,R.drawable.effects_10,R.drawable.effects_11,R.drawable.effects_12,R.drawable.effects_13,R.drawable.effects_14,R.drawable.effects_15,R.drawable.effects_16,R.drawable.effects_17,R.drawable.effects_18,R.drawable.effects_19,R.drawable.effects_20,R.drawable.effects_21,R.drawable.effects_22,R.drawable.effects_23,R.drawable.effects_24,R.drawable.effects_25,R.drawable.effects_26,R.drawable.effects_27,R.drawable.effects_28,R.drawable.effects_29,R.drawable.effects_30,R.drawable.effects_31,R.drawable.effects_32,R.drawable.effects_33,R.drawable.effects_34,R.drawable.effects_35,R.drawable.effects_36,R.drawable.effects_37,R.drawable.effects_38,R.drawable.effects_39,R.drawable.effects_40,R.drawable.effects_41,R.drawable.effects_42,R.drawable.effects_43,R.drawable.effects_44,R.drawable.effects_45,R.drawable.effects_46,R.drawable.effects_47,R.drawable.effects_48,R.drawable.effects_49,R.drawable.effects_50,R.drawable.effects_51,R.drawable.effects_52,R.drawable.effects_53,R.drawable.effects_54,R.drawable.effects_55,R.drawable.effects_56,R.drawable.effects_57,R.drawable.effects_58,R.drawable.effects_59,R.drawable.effects_60,R.drawable.effects_61,R.drawable.effects_62,R.drawable.effects_63,R.drawable.effects_64,R.drawable.effects_65,R.drawable.effects_66,R.drawable.effects_67,R.drawable.effects_68,R.drawable.effects_69,R.drawable.effects_70,R.drawable.effects_71};
//    public  int[] shapesIDs = {R.drawable.shapeslg1,R.drawable.shapes_lg2,R.drawable.shapes_lg3,R.drawable.shapes_lg4,R.drawable.shapes_lg5,R.drawable.shapes_lg6,R.drawable.shapes_lg7,R.drawable.shapes_lg8,R.drawable.shapes_lg9,R.drawable.shapes_lg10,R.drawable.shapes_lg11,R.drawable.shapes_lg12,R.drawable.shapes_lg13,R.drawable.shapes_lg14,R.drawable.shapes_lg15,R.drawable.shapes_lg16,R.drawable.shapes_lg17,R.drawable.shapes_lg18,R.drawable.shapes_lg19,R.drawable.shapes_lg20,R.drawable.shapes_lg21,R.drawable.shapes_lg22,R.drawable.shapes_lg23,R.drawable.shapes_lg24,R.drawable.shapes_lg25,R.drawable.shapes_lg26,R.drawable.shapes_lg27,R.drawable.shapes_lg28,R.drawable.shapes_lg29,R.drawable.shapes_lg30,R.drawable.shapes_lg31,R.drawable.shapes_lg32,R.drawable.shapes_lg33,R.drawable.shapes_lg34,R.drawable.shapes_lg35,R.drawable.shapes_lg36,R.drawable.shapes_lg37,R.drawable.shapes_lg38,R.drawable.shapes_lg39,R.drawable.shapes_lg40,R.drawable.shapes_lg41,R.drawable.shapes_lg42,R.drawable.shapes_lg43,R.drawable.shapes_lg44,R.drawable.shapes_lg45,R.drawable.shapes_lg46,R.drawable.shapes_lg47,R.drawable.shapes_lg48,R.drawable.shapes_lg49,R.drawable.shapes_lg50,R.drawable.shapes_lg51,R.drawable.shapes_lg52,R.drawable.shapes_lg53,R.drawable.shapes_lg54,R.drawable.shapes_lg55,R.drawable.shapes_lg56,R.drawable.shapes_lg57,R.drawable.shapes_lg58,R.drawable.shapes_lg59,R.drawable.shapes_lg60,R.drawable.shapes_lg61,R.drawable.shapes_lg62,R.drawable.shapes_lg63,R.drawable.shapes_lg64,R.drawable.shapes_lg65,R.drawable.shapes_lg66,R.drawable.shapes_lg67,R.drawable.shapes_lg68,R.drawable.shapes_lg69,R.drawable.shapes_lg70,R.drawable.shapes_lg71,R.drawable.shapes_lg72,R.drawable.shapes_lg73,R.drawable.shapes_lg74,R.drawable.shapes_lg75,R.drawable.shapes_lg76,R.drawable.shapes_lg77,R.drawable.shapes_lg78,R.drawable.shapes_lg79,R.drawable.shapes_lg80,R.drawable.shapes_lg81,R.drawable.shapes_lg82,R.drawable.shapes_lg83,R.drawable.shapes_lg84,R.drawable.shapes_lg85,R.drawable.shapes_lg86,R.drawable.shapes_lg87,R.drawable.shapes_lg88,R.drawable.shapes_lg89,R.drawable.shapes_lg90,R.drawable.shapes_lg91,R.drawable.shapes_lg92,R.drawable.shapes_lg93,R.drawable.shapes_lg94,R.drawable.shapes_lg95,R.drawable.shapes_lg96,R.drawable.shapes_lg97,R.drawable.shapes_lg98,R.drawable.shapes_lg99,R.drawable.shapes_lg100,R.drawable.shapes_lg101,R.drawable.shapes_lg102,R.drawable.shapes_lg103,R.drawable.shapes_lg104,R.drawable.shapes_lg105,R.drawable.shapes_lg106,R.drawable.shapes_lg107,R.drawable.shapes_lg108,R.drawable.shapes_lg109,R.drawable.shapes_lg110,R.drawable.shapes_lg111,R.drawable.shapes_lg112,R.drawable.shapes_lg113,R.drawable.shapes_lg114,R.drawable.shapes_lg115,R.drawable.shapes_lg116,R.drawable.shapes_lg117,R.drawable.shapes_lg118,R.drawable.shapes_lg119,R.drawable.shapes_lg120,R.drawable.shapes_lg121,R.drawable.shapes_lg122,R.drawable.shapes_lg123,R.drawable.shapes_lg124,R.drawable.shapes_lg125,R.drawable.shapes_lg126,R.drawable.shapes_lg127,R.drawable.shapes_lg128,R.drawable.shapes_lg129,R.drawable.shapes_lg130,R.drawable.shapes_lg131,R.drawable.shapes_lg132,R.drawable.shapes_lg133,R.drawable.shapes_lg134,R.drawable.shapes_lg135,R.drawable.shapes_lg136,R.drawable.shapes_lg137,R.drawable.shapes_lg138,R.drawable.shapes_lg139,R.drawable.shapes_lg140,R.drawable.shapes_lg141,R.drawable.shapes_lg142,R.drawable.shapes_lg143,R.drawable.shapes_lg144,R.drawable.shapes_lg145,R.drawable.shapes_lg146,R.drawable.shapes_lg147,R.drawable.shapes_lg148,R.drawable.shapes_lg149,R.drawable.shapes_lg150,R.drawable.shapes_lg151,R.drawable.shapes_lg152,R.drawable.shapes_lg153,R.drawable.shapes_lg154,R.drawable.shapes_lg155,R.drawable.shapes_lg156,R.drawable.shapes_lg157,R.drawable.shapes_lg158,R.drawable.shapes_lg159,R.drawable.shapes_lg160,R.drawable.shapes_lg161,R.drawable.shapes_lg162,R.drawable.shapes_lg163,R.drawable.shapes_lg164,R.drawable.shapes_lg165,R.drawable.shapes_lg166,R.drawable.shapes_lg167,R.drawable.shapes_lg168,R.drawable.shapes_lg169,R.drawable.shapes_lg170,R.drawable.shapes_lg171};
    public int[][] stickersIDs = {
        {//Stickers
                R.drawable.sticker1,R.drawable.sticker2,R.drawable.sticker3,R.drawable.sticker4,R.drawable.sticker5,R.drawable.sticker6,R.drawable.sticker7,R.drawable.sticker8,R.drawable.sticker9,R.drawable.sticker10,R.drawable.sticker11,R.drawable.sticker12,R.drawable.sticker13,R.drawable.sticker14,R.drawable.sticker15,R.drawable.sticker16,R.drawable.sticker17,R.drawable.sticker18,R.drawable.sticker19,R.drawable.sticker20,R.drawable.sticker21,R.drawable.sticker22,R.drawable.sticker23,R.drawable.sticker24,R.drawable.sticker25,R.drawable.sticker26,R.drawable.sticker27,R.drawable.sticker28,R.drawable.sticker29,R.drawable.sticker30,R.drawable.sticker31,R.drawable.sticker32,R.drawable.sticker33,R.drawable.sticker34,R.drawable.sticker35,R.drawable.sticker36,R.drawable.sticker37,R.drawable.sticker38,R.drawable.sticker39,R.drawable.sticker40,R.drawable.sticker41,R.drawable.sticker42,R.drawable.sticker43,R.drawable.sticker44,R.drawable.sticker45,R.drawable.sticker46,R.drawable.sticker47,R.drawable.sticker48,R.drawable.sticker49,R.drawable.sticker50,R.drawable.sticker51,R.drawable.sticker52,R.drawable.sticker53,R.drawable.sticker54,R.drawable.sticker55,R.drawable.sticker56,R.drawable.sticker57,R.drawable.sticker58,R.drawable.sticker59,R.drawable.sticker60,R.drawable.sticker61,R.drawable.sticker62,R.drawable.sticker63,R.drawable.sticker64,R.drawable.sticker65,R.drawable.sticker66,R.drawable.sticker67,R.drawable.sticker68,R.drawable.sticker69,R.drawable.sticker70,R.drawable.sticker71,R.drawable.sticker72,R.drawable.sticker73,R.drawable.sticker74,R.drawable.sticker75,R.drawable.sticker76,R.drawable.sticker77,R.drawable.sticker78,R.drawable.sticker79,R.drawable.sticker80,R.drawable.sticker81,R.drawable.sticker82,R.drawable.sticker83,R.drawable.sticker84,R.drawable.sticker85,R.drawable.sticker86,R.drawable.sticker87,R.drawable.sticker88,R.drawable.sticker89,R.drawable.sticker90,R.drawable.sticker91,R.drawable.sticker92,R.drawable.sticker93,R.drawable.sticker94,R.drawable.sticker95,R.drawable.sticker96,R.drawable.sticker97,R.drawable.sticker98,R.drawable.sticker99,R.drawable.sticker100,R.drawable.sticker101,R.drawable.sticker102,R.drawable.sticker103,R.drawable.sticker104,R.drawable.sticker105,R.drawable.sticker106,R.drawable.sticker107,R.drawable.sticker108,R.drawable.sticker109,R.drawable.sticker110,R.drawable.sticker111,R.drawable.sticker112,R.drawable.sticker113,R.drawable.sticker114,R.drawable.sticker115,R.drawable.sticker116,R.drawable.sticker117,R.drawable.sticker118,R.drawable.sticker119,R.drawable.sticker120,R.drawable.sticker121,R.drawable.sticker122,R.drawable.sticker123,R.drawable.sticker124,R.drawable.sticker125,R.drawable.sticker126,R.drawable.sticker127,R.drawable.sticker128,R.drawable.sticker129,R.drawable.sticker130,R.drawable.sticker131,R.drawable.sticker132,R.drawable.sticker133,R.drawable.sticker134,R.drawable.sticker135,R.drawable.sticker136,R.drawable.sticker137,R.drawable.sticker138,R.drawable.sticker139,R.drawable.sticker140,R.drawable.sticker141,R.drawable.sticker142,R.drawable.sticker143,R.drawable.sticker144,R.drawable.sticker145,R.drawable.sticker146,R.drawable.sticker147,R.drawable.sticker148,R.drawable.sticker149,R.drawable.sticker150,R.drawable.sticker151,R.drawable.sticker152,R.drawable.sticker153,R.drawable.sticker154,R.drawable.sticker155,R.drawable.sticker156,R.drawable.sticker157,R.drawable.sticker158,R.drawable.sticker159,R.drawable.sticker160,R.drawable.sticker161,R.drawable.sticker162,R.drawable.sticker163,R.drawable.sticker164,R.drawable.sticker165,R.drawable.sticker166,R.drawable.sticker167,R.drawable.sticker168,R.drawable.sticker169,R.drawable.sticker170,R.drawable.sticker171,R.drawable.sticker172,R.drawable.sticker173,R.drawable.sticker174,R.drawable.sticker175,R.drawable.sticker176,R.drawable.sticker177,R.drawable.sticker178,R.drawable.sticker179,R.drawable.sticker180,R.drawable.sticker181,R.drawable.sticker182,R.drawable.sticker183,R.drawable.sticker184,R.drawable.sticker185,R.drawable.sticker186,R.drawable.sticker187,R.drawable.sticker188,R.drawable.sticker189,R.drawable.sticker190,R.drawable.sticker191,R.drawable.sticker192,R.drawable.sticker193,R.drawable.sticker194,R.drawable.sticker195,R.drawable.sticker196,R.drawable.sticker197,R.drawable.sticker198,R.drawable.sticker199,R.drawable.sticker200,R.drawable.sticker201,R.drawable.sticker202,R.drawable.sticker203,R.drawable.sticker204,R.drawable.sticker205,R.drawable.sticker206,R.drawable.sticker207,R.drawable.sticker208,R.drawable.sticker209,R.drawable.sticker210,R.drawable.sticker211,R.drawable.sticker212,R.drawable.sticker213,R.drawable.sticker214,R.drawable.sticker215,R.drawable.sticker216,R.drawable.sticker217,R.drawable.sticker218,R.drawable.sticker219,R.drawable.sticker220,R.drawable.sticker221,R.drawable.sticker222,R.drawable.sticker223,R.drawable.sticker224,R.drawable.sticker225,R.drawable.sticker226,R.drawable.sticker227,R.drawable.sticker228,R.drawable.sticker229,R.drawable.sticker230,R.drawable.sticker231,R.drawable.sticker232,R.drawable.sticker233,R.drawable.sticker234,R.drawable.sticker235,R.drawable.sticker236,R.drawable.sticker237,R.drawable.sticker238,R.drawable.sticker239,R.drawable.sticker240,R.drawable.sticker241,R.drawable.sticker242,R.drawable.sticker243,R.drawable.sticker244,R.drawable.sticker245,R.drawable.sticker246,R.drawable.sticker247,R.drawable.sticker248,R.drawable.sticker249,R.drawable.sticker250,R.drawable.sticker251,R.drawable.shapes_lg1,R.drawable.shapes_lg2,R.drawable.shapes_lg3,R.drawable.shapes_lg4,R.drawable.shapes_lg5,R.drawable.shapes_lg6,R.drawable.shapes_lg7,R.drawable.shapes_lg8,R.drawable.shapes_lg9,R.drawable.shapes_lg10,R.drawable.shapes_lg11,R.drawable.shapes_lg12,R.drawable.shapes_lg13,R.drawable.shapes_lg14,R.drawable.shapes_lg15,R.drawable.shapes_lg16,R.drawable.shapes_lg17,R.drawable.shapes_lg18,R.drawable.shapes_lg19,R.drawable.shapes_lg20,R.drawable.shapes_lg21,R.drawable.shapes_lg22,R.drawable.shapes_lg23,R.drawable.shapes_lg24,R.drawable.shapes_lg25,R.drawable.shapes_lg26,R.drawable.shapes_lg27,R.drawable.shapes_lg28,R.drawable.shapes_lg29,R.drawable.shapes_lg30,R.drawable.shapes_lg31,R.drawable.shapes_lg32,R.drawable.shapes_lg33,R.drawable.shapes_lg34,R.drawable.shapes_lg35,R.drawable.shapes_lg36,R.drawable.shapes_lg37,R.drawable.shapes_lg38,R.drawable.shapes_lg39,R.drawable.shapes_lg40,R.drawable.shapes_lg41,R.drawable.shapes_lg42,R.drawable.shapes_lg43,R.drawable.shapes_lg44,R.drawable.shapes_lg45,R.drawable.shapes_lg46,R.drawable.shapes_lg47,R.drawable.shapes_lg48,R.drawable.shapes_lg49,R.drawable.shapes_lg50,R.drawable.shapes_lg51,R.drawable.shapes_lg52,R.drawable.shapes_lg53,R.drawable.shapes_lg54,R.drawable.shapes_lg55,R.drawable.shapes_lg56,R.drawable.shapes_lg57,R.drawable.shapes_lg58,R.drawable.shapes_lg59,R.drawable.shapes_lg60,R.drawable.shapes_lg61,R.drawable.shapes_lg62,R.drawable.shapes_lg63,R.drawable.shapes_lg64,R.drawable.shapes_lg65,R.drawable.shapes_lg66,R.drawable.shapes_lg67,R.drawable.shapes_lg68,R.drawable.shapes_lg69,R.drawable.shapes_lg70,R.drawable.shapes_lg71,R.drawable.shapes_lg72,R.drawable.shapes_lg73,R.drawable.shapes_lg74,R.drawable.shapes_lg75,R.drawable.shapes_lg76,R.drawable.shapes_lg77,R.drawable.shapes_lg78,R.drawable.shapes_lg79,R.drawable.shapes_lg80,R.drawable.shapes_lg83,R.drawable.shapes_lg89,R.drawable.shapes_lg90,R.drawable.shapes_lg93,R.drawable.shapes_lg94,R.drawable.shapes_lg95,R.drawable.shapes_lg96,R.drawable.shapes_lg97,R.drawable.shapes_lg98,R.drawable.shapes_lg99,R.drawable.shapes_lg102,R.drawable.shapes_lg103,R.drawable.shapes_lg104,R.drawable.shapes_lg105,R.drawable.shapes_lg106,R.drawable.shapes_lg107,R.drawable.shapes_lg116,R.drawable.shapes_lg119,R.drawable.shapes_lg120,R.drawable.shapes_lg122,R.drawable.shapes_lg127,R.drawable.shapes_lg128,R.drawable.shapes_lg129,R.drawable.shapes_lg130,R.drawable.shapes_lg131,R.drawable.shapes_lg132,R.drawable.shapes_lg133,R.drawable.shapes_lg134,R.drawable.shapes_lg135,R.drawable.shapes_lg136,R.drawable.shapes_lg143,R.drawable.shapes_lg144,R.drawable.shapes_lg145,R.drawable.shapes_lg146,R.drawable.shapes_lg147,R.drawable.shapes_lg148,R.drawable.shapes_lg149,R.drawable.shapes_lg150,R.drawable.shapes_lg151,R.drawable.shapes_lg152,R.drawable.shapes_lg153,R.drawable.shapes_lg154,R.drawable.shapes_lg155,R.drawable.shapes_lg156,R.drawable.shapes_lg157,R.drawable.shapes_lg158,R.drawable.shapes_lg159,R.drawable.shapes_lg160,R.drawable.shapes_lg161,R.drawable.shapes_lg162,R.drawable.shapes_lg163,R.drawable.shapes_lg164,R.drawable.shapes_lg165,R.drawable.shapes_lg166,R.drawable.shapes_lg167,R.drawable.shapes_lg168,R.drawable.shapes_lg169,R.drawable.shapes_lg170,R.drawable.shapes_lg171
        },
        {//Flags
                R.drawable.flg1,R.drawable.flg2,R.drawable.flg3,R.drawable.flg4,R.drawable.flg5,R.drawable.flg6,R.drawable.flg7,R.drawable.flg8,R.drawable.flg9,R.drawable.flg10,R.drawable.flg11,R.drawable.flg12,R.drawable.flg13,R.drawable.flg14,R.drawable.flg15,R.drawable.flg16,R.drawable.flg17,R.drawable.flg18,R.drawable.flg19,R.drawable.flg20,R.drawable.flg21,R.drawable.flg22,R.drawable.flg23,R.drawable.flg24,R.drawable.flg25,R.drawable.flg26,R.drawable.flg27,R.drawable.flg28,R.drawable.flg29,R.drawable.flg30,R.drawable.flg31,R.drawable.flg32,R.drawable.flg33,R.drawable.flg34,R.drawable.flg35,R.drawable.flg36,R.drawable.flg37,R.drawable.flg38,R.drawable.flg39,R.drawable.flg40,R.drawable.flg41,R.drawable.flg42,R.drawable.flg43,R.drawable.flg44,R.drawable.flg45,R.drawable.flg46,R.drawable.flg47,R.drawable.flg48,R.drawable.flg49,R.drawable.flg50,R.drawable.flg51,R.drawable.flg52,R.drawable.flg53,R.drawable.flg54,R.drawable.flg55,R.drawable.flg56,R.drawable.flg57,R.drawable.flg58,R.drawable.flg59,R.drawable.flg60,R.drawable.flg61,R.drawable.flg62,R.drawable.flg63,R.drawable.flg64,R.drawable.flg65,R.drawable.flg66,R.drawable.flg67,R.drawable.flg68,R.drawable.flg69,R.drawable.flg70,R.drawable.flg71,R.drawable.flg72,R.drawable.flg73,R.drawable.flg74,R.drawable.flg75,R.drawable.flg76,R.drawable.flg77,R.drawable.flg78,R.drawable.flg79,R.drawable.flg80,R.drawable.flg81,R.drawable.flg82,R.drawable.flg83,R.drawable.flg84,R.drawable.flg85,R.drawable.flg86,R.drawable.flg87,R.drawable.flg88,R.drawable.flg89,R.drawable.flg90,R.drawable.flg91,R.drawable.flg92,R.drawable.flg93,R.drawable.flg94,R.drawable.flg95,R.drawable.flg96,R.drawable.flg97,R.drawable.flg98,R.drawable.flg99,R.drawable.flg100,R.drawable.flg101,R.drawable.flg102,R.drawable.flg103,R.drawable.flg104,R.drawable.flg105,R.drawable.flg106,R.drawable.flg107,R.drawable.flg108,R.drawable.flg109,R.drawable.flg110,R.drawable.flg111,R.drawable.flg112,R.drawable.flg113,R.drawable.flg114,R.drawable.flg115,R.drawable.flg116,R.drawable.flg117,R.drawable.flg118,R.drawable.flg119,R.drawable.flg120,R.drawable.flg121,R.drawable.flg122,R.drawable.flg123,R.drawable.flg124,R.drawable.flg125,R.drawable.flg126,R.drawable.flg127,R.drawable.flg128,R.drawable.flg129,R.drawable.flg130,R.drawable.flg131,R.drawable.flg132,R.drawable.flg133,R.drawable.flg134,R.drawable.flg135,R.drawable.flg136,R.drawable.flg137,R.drawable.flg138,R.drawable.flg139,R.drawable.flg140,R.drawable.flg141,R.drawable.flg142,R.drawable.flg143,R.drawable.flg144,R.drawable.flg145,R.drawable.flg146,R.drawable.flg147,R.drawable.flg148,R.drawable.flg149,R.drawable.flg150,R.drawable.flg151,R.drawable.flg152,R.drawable.flg153,R.drawable.flg154,R.drawable.flg155,R.drawable.flg156,R.drawable.flg157,R.drawable.flg158,R.drawable.flg159,R.drawable.flg160,R.drawable.flg161,R.drawable.flg162,R.drawable.flg163,R.drawable.flg164,R.drawable.flg165,R.drawable.flg166,R.drawable.flg167,R.drawable.flg168,R.drawable.flg169,R.drawable.flg170,R.drawable.flg171,R.drawable.flg172,R.drawable.flg173,R.drawable.flg174,R.drawable.flg175,R.drawable.flg176,R.drawable.flg177,R.drawable.flg178,R.drawable.flg179,R.drawable.flg180,R.drawable.flg181,R.drawable.flg182,R.drawable.flg183,R.drawable.flg184,R.drawable.flg185,R.drawable.flg186,R.drawable.flg187
        },
        {//Sports
                R.drawable.l1,R.drawable.l2,R.drawable.l3,R.drawable.l4,R.drawable.l5,R.drawable.l6,R.drawable.l7,R.drawable.l8,R.drawable.l9,R.drawable.l10,R.drawable.l11,R.drawable.l12,R.drawable.l13,R.drawable.l14,R.drawable.l15,R.drawable.l16,R.drawable.l17,R.drawable.l18,R.drawable.l19,R.drawable.l20,R.drawable.l21,R.drawable.l22,R.drawable.l23,R.drawable.l24,R.drawable.l25,R.drawable.l26,R.drawable.l27,R.drawable.l28,R.drawable.l29,R.drawable.l30,R.drawable.l31,R.drawable.l32,R.drawable.l33,R.drawable.l34,R.drawable.l35,R.drawable.l36,R.drawable.l37,R.drawable.l38,R.drawable.l39,R.drawable.l40,R.drawable.l41,R.drawable.l42,R.drawable.l43,R.drawable.l44,R.drawable.l45,R.drawable.l46,R.drawable.l47,R.drawable.l48,R.drawable.l49,R.drawable.l50,R.drawable.l51,R.drawable.l52,R.drawable.l53,R.drawable.l54,R.drawable.l55,R.drawable.l56,R.drawable.l57,R.drawable.l58,R.drawable.l59,R.drawable.l60,R.drawable.l61,R.drawable.l62,R.drawable.l63,R.drawable.l64,R.drawable.l65,R.drawable.l66,R.drawable.l67,R.drawable.l68,R.drawable.l69,R.drawable.l70,R.drawable.l71,R.drawable.l72,R.drawable.l73
        },
        {//Foods
                R.drawable.food1,R.drawable.food2,R.drawable.food3,R.drawable.food4,R.drawable.food5,R.drawable.food6,R.drawable.food7,R.drawable.food8,R.drawable.food9,R.drawable.food10,R.drawable.food11,R.drawable.food12,R.drawable.food13,R.drawable.food14,R.drawable.food15,R.drawable.food16,R.drawable.food17,R.drawable.food18,R.drawable.food19,R.drawable.food20,R.drawable.food21,R.drawable.food22,R.drawable.food23,R.drawable.food24,R.drawable.food25,R.drawable.food26,R.drawable.food27,R.drawable.food28,R.drawable.food29,R.drawable.food30,R.drawable.food31,R.drawable.food32,R.drawable.food33,R.drawable.food34,R.drawable.food35,R.drawable.food36,R.drawable.food37,R.drawable.food38,R.drawable.food39,R.drawable.food40,R.drawable.food41,R.drawable.food42,R.drawable.food43,R.drawable.food44,R.drawable.food45,R.drawable.food46,R.drawable.food47
        }

    };
//    public int[] textures = {R.drawable.bg_texture_1,R.drawable.bg_texture_2,R.drawable.bg_texture_3,R.drawable.bg_texture_4,R.drawable.bg_texture_5,R.drawable.bg_texture_6,R.drawable.bg_texture_7,R.drawable.bg_texture_8,R.drawable.bg_texture_9,R.drawable.bg_texture_10, };



    ImageButton framesBtn, tshirtsBtn, textBtn, galleryBtn, edittextImgBtn, changeColorImgBtn, textShadowImgBtn, changeFontImgBtn, textShadowColorPicker, changeTextOpacityImgBtn, addStickerBtn, saveImageBtn;
    ImageView imageviewTshirt, imageview_filter,backArrowBtn;
    StickerView stickerView;
    RelativeLayout layout_artboard;
    public static Uri pickedImage;
    int textShadowRadius, textShadowDx, textShadowDy, textShadowColor;

    ImageView shareAppTv, proVersionAdFree;
    RelativeLayout stickerLayout;
    LinearLayout linearlayoutGradients;


    //Relative Layout
    RecyclerView recyclerView_filters;
    RecyclerAdapter adapter_filters;
    RelativeLayout linearlayoutBgOptions, linearLayout_offlineBgs, linearLayoutColors, linearLayoutFonts, linearLayoutFilters, layoutTextWorkingsLayout, linearLayoutTextOpacity, linearlayoutStickers, linearlayoutStickerColors;
    LinearLayout shadowAdjustmnetLayout, linearlayouttextAlignment,linearlayoutStickerAdjustment;

    RelativeLayout offlineBgsBtn, colorBgsBtn, gradientBgsBtn, filterBgBtn;
    ProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

         progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading");
        progressDialog.show();

        linkXml();
        getOnlineShirts(0);
        getOnlineBgs();


//        shirtAdapter.notifyDataSetChanged();


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                clickListener();

                if(interstitialAdImplement == null)
                {
                    interstitialAdImplement = new InterstitialAdImplement(EditorActivity.this );
                }
                else {
                    Random random = new Random();
                    int rand = random.nextInt(3);
                    if(rand == 1)
                    {
                        appOpenManager.showAdIfAvailable();

                    }
                    else {
                        interstitialAdImplement.setActivityOpenAd(true);
                        interstitialAdImplement.showInterstitial();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                interstitialAdImplement.setActivityOpenAd(false);
                            }
                        }, 1000);
                    }
                }



                new BannerAdImplement(EditorActivity.this, findViewById(R.id.adView)).BannerAdLoad();


            }
        },200);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                progressDialog.dismiss();
            }
        },3000);




    }

    private void linkXml() {
        framesBtn = findViewById(R.id.framesBtn);
        filterBgBtn = findViewById(R.id.filterBgBtn);
        tshirtsBtn = findViewById(R.id.tshirtsBtn);
        textBtn = findViewById(R.id.addTextBtn);
        imageviewTshirt = findViewById(R.id.imageview_Bg);
        imageview_filter = findViewById(R.id.imageview_filter);
        edittextImgBtn = findViewById(R.id.edittextImgBtn);
        changeColorImgBtn = findViewById(R.id.changeColorImg);
        textShadowImgBtn = findViewById(R.id.shadowImg);
        changeFontImgBtn = findViewById(R.id.changeFontImgBtn);
        changeTextOpacityImgBtn = findViewById(R.id.changeTextOpacityImgBtn);
        textShadowColorPicker = findViewById(R.id.shadowColorpicker);
        addStickerBtn = findViewById(R.id.addStickerImgBtn);
        saveImageBtn = findViewById(R.id.saveImgBtn);
        shareAppTv = findViewById(R.id.shareappTv);
        proVersionAdFree = findViewById(R.id.proVersionAdFree);
        backArrowBtn = findViewById(R.id.backArrowBtn);
        galleryBtn = findViewById(R.id.galleryBtn);

        offlineBgsBtn = findViewById(R.id.offlineBgsBtn);
        colorBgsBtn = findViewById(R.id.colorBgBtn);
        gradientBgsBtn = findViewById(R.id.gradientBgBtn);


        drawableFrameBg = R.drawable.tshirt30;

        shirtAdapter = new FrameAdapter(EditorActivity.this, tshirtsLinks, SHIRT_ADAPTER, new FrameListener() {
            @Override
            public void OnClick(int position) {
                Glide.with(EditorActivity.this).load(shirtAdapter.list.get(position)).into(imageviewTshirt);
            }
        });
        stickerView = findViewById(R.id.stickerview);
        stickerLayout = findViewById(R.id.stickerLayout);
        shadowAdjustmnetLayout = findViewById(R.id.shadowAdjustlayout);

//        gradColor1 = R.color.red;
//        gradColor2 = R.color.blue;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            gradColor1 = getColor(R.color.red);
            gradColor2 = getColor(R.color.blue);
        }




        stickerView.setOnStickerOperationListener(new StickerView.OnStickerOperationListener() {
            @Override
            public void onStickerAdded(@NonNull Sticker sticker) {
                stickerView.showBorder = true;
                stickerView.showIcons = true;
                stickerView.invalidate();

                if(stickerLayout.getVisibility() == View.GONE)
                {
                    stickerLayout.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onStickerClicked(@NonNull Sticker sticker) {
                stickerView.showBorder = true;
                stickerView.showIcons = true;
                invisibleAllLists();
                defaultMainButtonsNoClick();
                stickerView.invalidate();
                if (stickerLayout.getVisibility() == View.GONE) {
                    stickerLayout.setVisibility(View.VISIBLE);
                }

                if (stickerView.getCurrentSticker() instanceof TextSticker) {
                    //if Text is selected
                    textWorkings();
                    if(linearlayouttextAlignment.getVisibility() == View.GONE)
                    {
                        linearlayouttextAlignment.setVisibility(View.VISIBLE);
                    }
                    textAlignmentWorking();

                } else if (stickerView.getCurrentSticker() instanceof DrawableSticker) {
                    //If Sticker is selected
                    shapesAdjustmentWorking();
                }

            }

            @Override
            public void onStickerDeleted(@NonNull Sticker sticker) {
                if(sticker instanceof  DrawableSticker)
                {
                    if(linearlayoutStickerAdjustment.getVisibility() == View.VISIBLE)
                    {
                        linearlayoutStickerAdjustment.setVisibility(View.GONE);
                    }
                    linearlayoutStickers.setVisibility(View.VISIBLE);
                    stickersWorking();
                }

            }

            @Override
            public void onStickerDragFinished(@NonNull Sticker sticker) {

            }

            @Override
            public void onStickerTouchedDown(@NonNull Sticker sticker) {

            }

            @Override
            public void onStickerZoomFinished(@NonNull Sticker sticker) {

            }

            @Override
            public void onStickerFlipped(@NonNull Sticker sticker) {

            }

            @Override
            public void onStickerDoubleTapped(@NonNull Sticker sticker) {
                if (stickerView.getCurrentSticker() instanceof TextSticker) {
                    editText();
                }
            }
        });


        textShadowColor = getResources().getColor(R.color.black);
        textShadowDx = 0;
        textShadowDy = 0;
        textShadowRadius = 0;
        activityOpenAd = 0;

//        stickerPramsEditableLayout =  findViewById(R.id.png"ManagementLayout);
//        Display display = getWindowManager().getDefaultDisplay();
//        DisplayMetrics displayMetrics = new DisplayMetrics();
//        display.getMetrics(displayMetrics);
//        int width = displayMetrics.widthPixels;
//        stickerPramsEditableLayout.getLayoutParams().height = width;
//        stickerPramsEditableLayout.getLayoutParams().width = width;

//        imageviewBg.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                stickerView.showBorder = false;
//                stickerView.showIcons = false;
//                stickerView.invalidate();
//                return true;
//            }
//        });

//        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(width, width);
//        stickerPramsEditableLayout.setLayoutParams(layoutParams);


        RecyclerView recyclerViewCategoriesShirt = findViewById(R.id.recyclerview_categoriesShirts);
        if(shirtCategoryNames.length <= 1)
        {
            recyclerViewCategoriesShirt.setVisibility(View.GONE);
        }
        else {
            CategoryAdapter adapter = new CategoryAdapter(EditorActivity.this, shirtCategoryNames, new CategoryAdapter.CategoryListener() {
                @Override
                public void onItemSelected(int position) {
                    getOnlineShirts(position);
                }
            });
            recyclerViewCategoriesShirt.setVisibility(View.VISIBLE);
            recyclerViewCategoriesShirt.setLayoutManager(new LinearLayoutManager(EditorActivity.this, LinearLayoutManager.HORIZONTAL, false));
            recyclerViewCategoriesShirt.setAdapter(adapter);
        }

        fbInterstitialReward = 0;


        layout_artboard = findViewById(R.id.layoutArtboardJpg);

        linearLayout_offlineBgs = findViewById(R.id.linearlayout_bgs_data);
        linearlayoutBgOptions = findViewById(R.id.linearlayout_bgsOptions);
        linearlayouttextAlignment = findViewById(R.id.linearlayout_textAlignment);
        linearLayoutColors = findViewById(R.id.linearlayout_colors);
        linearLayoutFonts = findViewById(R.id.linearlayout_fonts);
        linearLayoutFilters = findViewById(R.id.linearlayout_filters);
        linearlayoutStickers = findViewById(R.id.linearlayout_stickers);
        linearlayoutStickerAdjustment = findViewById(R.id.linearlayout_StickerAdjustments);
        linearlayoutStickerColors = findViewById(R.id.linearlayout_stickerColors);
        linearlayoutGradients = findViewById(R.id.linearlayoutGradients);

        linearLayoutTextOpacity = findViewById(R.id.linearlayoutTextOpacity);

        layoutTextWorkingsLayout = findViewById(R.id.linearlayout_textAdjustments);

        recyclerView_filters = findViewById(R.id.recyclerview_filters);

            setSquareMatrixFrame();

        invisibleAllLists();
        addText("Your Text");

        if(linearLayout_offlineBgs.getVisibility() == View.GONE)
        {
            linearLayout_offlineBgs.setVisibility(View.VISIBLE);
        }
        if(linearlayoutBgOptions.getVisibility() == View.GONE)
        {
            linearlayoutBgOptions.setVisibility(View.VISIBLE);
        }

        framesBtn.setImageResource(R.drawable.bg_clicked);
        int padding = dpToPx(8);
        framesBtn.setPadding(padding,padding,padding,padding);
        offlineBgsWorking();
    }


    public void changeBitmapPattern( int texture) {

        Bitmap tShirtBitmap = ((BitmapDrawable) imageviewTshirt.getDrawable()).getBitmap();
        Bitmap textureBitmap = BitmapFactory.decodeResource(getResources(), texture);

        Paint paint = new Paint();
        paint.setDither(true);
        paint.setAntiAlias(true);
        Bitmap createBitmap = Bitmap.createBitmap(tShirtBitmap.getWidth(), tShirtBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(createBitmap);
        canvas.drawBitmap(tShirtBitmap, 0.0f, 0.0f, (Paint) null);
        paint.setShader(new BitmapShader(textureBitmap, Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawRect(0.0f, 0.0f, (float) tShirtBitmap.getWidth(), (float) tShirtBitmap.getHeight(), paint);


        imageviewTshirt.setImageBitmap(createBitmap);
    }

    private void setTextTexture(int texture)
    {
        //textTexture
        try {
            if (stickerView.getCurrentSticker() instanceof TextSticker) {
                ((TextSticker) stickerView.getCurrentSticker()).textPaint.setShader(new BitmapShader(BitmapFactory.decodeResource(getResources(), texture), Shader.TileMode.REPEAT, Shader.TileMode.REPEAT));
                ((TextSticker) stickerView.getCurrentSticker()).resizeText();
                stickerView.invalidate();
            } else {
                Toast.makeText(EditorActivity.this, "Select Text", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setSquareMatrixFrame()
    {
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;

//        ((RelativeLayout.LayoutParams)layout_artboard.getLayoutParams()).removeRule(RelativeLayout.ABOVE);

        layout_artboard.getLayoutParams().height = width;
        layout_artboard.getLayoutParams().width = width;
    }

    private ArrayList<String> convertDrawablestoUri(int[] drawables)
    {
        ArrayList<String> arrayList = new ArrayList<>();
        Resources res = getResources();
        for(int i=0;i<drawables.length;i++)
        {
            Uri imageUri = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + res.getResourcePackageName(drawables[i]) + '/' + res.getResourceTypeName(drawables[i]) + '/' + res.getResourceEntryName(drawables[i]));
            arrayList.add(imageUri.toString());
        }


        return  arrayList;
    }

    private void getOnlineShirts(int position)
    {
        tshirtsLinks.clear();
        for(int i = 0; i< shirtLink[position].length; i++)
        {
            tshirtsLinks.add(shirtLink[position][i]);
        }
        shirtAdapter.notifyDataSetChanged();
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageRef = storage.getReference();
//
//        StorageReference logoNameRef = storageRef.child("tshirts");
//        logoNameRef.listAll()
//                .addOnSuccessListener(listResult -> {
//                    for (StorageReference item : listResult.getItems()) {
//                        item.getDownloadUrl().addOnSuccessListener(uri -> {
//                            String imageUrl = uri.toString();
//                            tshirtsLinks.add(imageUrl);
//                            shirtAdapter.notifyDataSetChanged();
//                        });
//                    }
//                })
//                .addOnFailureListener(exception -> {
//                });
    }

    private void getOnlineBgs()
    {

        for(int i=0;i<bgShirtLink.length;i++)
        {
            bgsLinks.add(bgShirtLink[i]);
        }
        bgsAdapter.notifyDataSetChanged();
//        FirebaseStorage storage = FirebaseStorage.getInstance();
//        StorageReference storageRef = storage.getReference();
//
//        StorageReference logoNameRef = storageRef.child("bgs");
//        logoNameRef.listAll()
//                .addOnSuccessListener(listResult -> {
//                    progressDialog.dismiss();
//                    for (StorageReference item : listResult.getItems()) {
//                        item.getDownloadUrl().addOnSuccessListener(uri -> {
//                            String imageUrl = uri.toString();
//                            bgsLinks.add(imageUrl);
//                            bgsAdapter.notifyDataSetChanged();
//                        });
//                    }
//                })
//                .addOnFailureListener(exception -> {
//                    progressDialog.dismiss();
//                    Toast.makeText(this, "Error : " + exception.getMessage(), Toast.LENGTH_SHORT).show();
//                });
    }



    private void showFbInterstitialForReward(Object drawable) {
        drawableFrameBg = drawable;
        fbInterstitialReward = 1;
        showInterstitial();
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (fbAdView != null)
        {
            fbAdView.destroy();
        }
    }




    @SuppressLint("ClickableViewAccessibility")
    private void watchAdToUnlockShirt(String drawableId) {
        Dialog dialog = new Dialog(EditorActivity.this);
        dialog.setContentView(R.layout.watchad_dialoge);
        dialog.show();
        ImageView buttonCancel = dialog.findViewById(R.id.dialogBtnCancel);
        ImageView buttonPurchaseApp = dialog.findViewById(R.id.dialogBtnPurchaseApp);
        ImageView buttonOk = dialog.findViewById(R.id.dialogBtnOk);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setCancelable(false);



        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadAdmobRewarded(drawableId);
//                showFbInterstitialForReward(drawableId);
                dialog.dismiss();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        buttonPurchaseApp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                inApp.startPurchase();
            }
        });


    }

    private void loadAdmobRewarded(String drawableId) {
        ProgressDialog dialog = new ProgressDialog(this);
        dialog.setMessage("Loading Ad, Please Wait...");
        dialog.setCancelable(false);
        dialog.show();

        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, String.valueOf(getResources().getString(R.string.admob_rewarded)),
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        dialog.dismiss();
                        Log.d(TAG, loadAdError.toString());
                        Toast.makeText(EditorActivity.this, "Error Loading Ad", Toast.LENGTH_SHORT).show();
                        mRewardedAd = null;
                    }

                    @Override
                     public void onAdLoaded(@NonNull RewardedAd ad) {
                        dialog.dismiss();
                        mRewardedAd = ad;
                        Log.d(TAG, "Ad was loaded.");
                        showAdmobRewarded(drawableId);

                    }
                });
    }

    private void showAdmobRewarded(String drawablelink)
    {
        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdClicked() {
                // Called when a click is recorded for an ad.
                Log.d(TAG, "Ad was clicked.");
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Set the ad reference to null so you don't show the ad a second time.
                Log.d(TAG, "Ad dismissed fullscreen content.");
                mRewardedAd = null;
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                // Called when ad fails to show.
                Log.e(TAG, "Ad failed to show fullscreen content.");
                mRewardedAd = null;
            }

            @Override
            public void onAdImpression() {
                // Called when an impression is recorded for an ad.
                Log.d(TAG, "Ad recorded an impression.");
            }

            @Override
            public void onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad showed fullscreen content.");


            }
        });

        mRewardedAd.show(EditorActivity.this, new OnUserEarnedRewardListener() {
            @Override
            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                Glide.with(EditorActivity.this).load(drawablelink).into(imageviewTshirt);
            }
        });
    }


    private void invisibleList1() {
        if (linearlayoutBgOptions.getVisibility() == View.VISIBLE) {
            linearlayoutBgOptions.setVisibility(View.GONE);
        }
        if (linearLayoutFilters.getVisibility() == View.VISIBLE) {
            linearLayoutFilters.setVisibility(View.GONE);
        }
        if (linearlayoutStickers.getVisibility() == View.VISIBLE) {
            linearlayoutStickers.setVisibility(View.GONE);
        }

    }

    private void invisibleList2() {
        if (linearLayout_offlineBgs.getVisibility() == View.VISIBLE) {
            linearLayout_offlineBgs.setVisibility(View.GONE);
        }
        if(linearlayouttextAlignment.getVisibility() == View.VISIBLE)
        {
            linearlayouttextAlignment.setVisibility(View.GONE);
        }

        if (linearLayoutColors.getVisibility() == View.VISIBLE) {
            linearLayoutColors.setVisibility(View.GONE);
        }
        if (linearLayoutFonts.getVisibility() == View.VISIBLE) {
            linearLayoutFonts.setVisibility(View.GONE);
        }
        if (shadowAdjustmnetLayout.getVisibility() == View.VISIBLE) {
            shadowAdjustmnetLayout.setVisibility(View.GONE);
        }
        if (linearLayoutTextOpacity.getVisibility() == View.VISIBLE) {
            linearLayoutTextOpacity.setVisibility(View.GONE);
        }
        if (linearlayoutGradients.getVisibility() == View.VISIBLE) {
            linearlayoutGradients.setVisibility(View.GONE);
        }

        if (linearLayoutFilters.getVisibility() == View.VISIBLE) {
            linearLayoutFilters.setVisibility(View.GONE);
        }



    }

    public void invisibleAllLists(View view) {
        invisibleList1();
        invisibleList2();
    }

    public void invisibleAllLists() {
        invisibleList1();
        invisibleList2();
        layoutTextWorkingsLayout.setVisibility(View.GONE);
        linearlayoutStickerAdjustment.setVisibility(View.GONE);

    }


    private void clickListener() {
        framesBtn.setOnClickListener(v -> {
            invisibleAllLists();


            offlineBgsBtn.callOnClick();
            if(linearlayoutBgOptions.getVisibility() == View.GONE)
            {
                linearlayoutBgOptions.setVisibility(View.VISIBLE);
            }

            framesBtn.setImageResource(R.drawable.bg_clicked);
            int padding = dpToPx(8);
            framesBtn.setPadding(padding,padding,padding,padding);
//                galleryBtn.invalidate();

            int padding2 = dpToPx(15);
            galleryBtn.setImageResource(R.drawable.gallery);
            galleryBtn.setPadding(padding2,padding2,padding2,padding2);
            tshirtsBtn.setImageResource(R.drawable.tshirt);
            tshirtsBtn.setPadding(padding2,padding2,padding2,padding2);
            addStickerBtn.setImageResource(R.drawable.sticker);
            addStickerBtn.setPadding(padding2,padding2,padding2,padding2);
            textBtn.setImageResource(R.drawable.addtext);
            textBtn.setPadding(padding2,padding2,padding2,padding2);
            saveImageBtn.setImageResource(R.drawable.save);
            saveImageBtn.setPadding(padding2,padding2,padding2,padding2);

        });

        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                galleryBtn.setImageResource(R.drawable.gallery_clicked);
                int padding = dpToPx(8);
                galleryBtn.setPadding(padding,padding,padding,padding);
//                galleryBtn.invalidate();

                int padding2 = dpToPx(15);
                framesBtn.setImageResource(R.drawable.bg);
                framesBtn.setPadding(padding2,padding2,padding2,padding2);
                tshirtsBtn.setImageResource(R.drawable.tshirt);
                tshirtsBtn.setPadding(padding2,padding2,padding2,padding2);
                addStickerBtn.setImageResource(R.drawable.sticker);
                addStickerBtn.setPadding(padding2,padding2,padding2,padding2);
                textBtn.setImageResource(R.drawable.addtext);
                textBtn.setPadding(padding2,padding2,padding2,padding2);
                saveImageBtn.setImageResource(R.drawable.save);
                saveImageBtn.setPadding(padding2,padding2,padding2,padding2);


                showImportImageGalleryDialog();
            }
        });


        colorBgsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invisibleList2();
                if(linearLayoutColors.getVisibility() == View.GONE)
                {
                    linearLayoutColors.setVisibility(View.VISIBLE);
                }
                colorsWorking(Data.COLOR_FOR_BGS);

            }
        });

        filterBgBtn.setOnClickListener(v -> {
            invisibleList2();
            filtersWorking();
            if(linearLayoutFilters.getVisibility() == View.GONE) {
                linearLayoutFilters.setVisibility(View.VISIBLE);
            }

        });

        tshirtsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invisibleAllLists();
//                choosePhotoFromGallery(v);
                if(linearLayout_offlineBgs.getVisibility() == View.GONE)
                {
                    linearLayout_offlineBgs.setVisibility(View.VISIBLE);
                }

                tshirtsBtn.setImageResource(R.drawable.tshirt_clicked);
                int padding = dpToPx(8);
                tshirtsBtn.setPadding(padding,padding,padding,padding);
//                galleryBtn.invalidate();

                int padding2 = dpToPx(15);
                framesBtn.setImageResource(R.drawable.bg);
                framesBtn.setPadding(padding2,padding2,padding2,padding2);
                galleryBtn.setImageResource(R.drawable.gallery);
                galleryBtn.setPadding(padding2,padding2,padding2,padding2);
                addStickerBtn.setImageResource(R.drawable.sticker);
                addStickerBtn.setPadding(padding2,padding2,padding2,padding2);
                textBtn.setImageResource(R.drawable.addtext);
                textBtn.setPadding(padding2,padding2,padding2,padding2);
                saveImageBtn.setImageResource(R.drawable.save);
                saveImageBtn.setPadding(padding2,padding2,padding2,padding2);

                tshirtsWorking();
            }
        });

        textBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                invisibleAllLists();
                addTextDialog();

                textBtn.setImageResource(R.drawable.addtext_clicked);
                int padding = dpToPx(8);
                textBtn.setPadding(padding,padding,padding,padding);
//                galleryBtn.invalidate();

                int padding2 = dpToPx(15);
                framesBtn.setImageResource(R.drawable.bg);
                framesBtn.setPadding(padding2,padding2,padding2,padding2);
                tshirtsBtn.setImageResource(R.drawable.tshirt);
                tshirtsBtn.setPadding(padding2,padding2,padding2,padding2);
                addStickerBtn.setImageResource(R.drawable.sticker);
                addStickerBtn.setPadding(padding2,padding2,padding2,padding2);
                galleryBtn.setImageResource(R.drawable.gallery);
                galleryBtn.setPadding(padding2,padding2,padding2,padding2);
                saveImageBtn.setImageResource(R.drawable.save);
                saveImageBtn.setPadding(padding2,padding2,padding2,padding2);
            }
        });

        addStickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invisibleAllLists();
                linearlayoutStickers.setVisibility(View.VISIBLE);
                stickersWorking();

                addStickerBtn.setImageResource(R.drawable.sticker_clicked);
                int padding = dpToPx(8);
                addStickerBtn.setPadding(padding,padding,padding,padding);
//                galleryBtn.invalidate();

                int padding2 = dpToPx(15);
                framesBtn.setImageResource(R.drawable.bg);
                framesBtn.setPadding(padding2,padding2,padding2,padding2);
                tshirtsBtn.setImageResource(R.drawable.tshirt);
                tshirtsBtn.setPadding(padding2,padding2,padding2,padding2);
                galleryBtn.setImageResource(R.drawable.gallery);
                galleryBtn.setPadding(padding2,padding2,padding2,padding2);
                textBtn.setImageResource(R.drawable.addtext);
                textBtn.setPadding(padding2,padding2,padding2,padding2);
                saveImageBtn.setImageResource(R.drawable.save);
                saveImageBtn.setPadding(padding2,padding2,padding2,padding2);

            }
        });

        shareAppTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                func_shareapp();
            }
        });

        proVersionAdFree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                inApp.showDialog(EditorActivity.this);
            }
        });

        saveImageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInterstitial();
                stickerView.showBorder = false;
                stickerView.showIcons = false;
                stickerView.invalidate();

                Dialog dialog = new Dialog(EditorActivity.this);
                dialog.setContentView(R.layout.import_picture_dialog);
                dialog.show();

                if (dialog.getWindow() != null)
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                TextView saveasPng = dialog.findViewById(R.id.importAsBackground);
                TextView saveAsJpg = dialog.findViewById(R.id.importAsSticker);

                saveasPng.setText("Save as Png");
                saveAsJpg.setText("Save as Jpg");

                saveasPng.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Bitmap imagetosave = new SaveImage(EditorActivity.this,findViewById(R.id.layoutArtboardPng)).saveImageAsPng();

                        showSaveImageLayout(imagetosave);
                    }
                });

                saveAsJpg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Bitmap imagetosave = new SaveImage(EditorActivity.this,findViewById(R.id.layoutArtboardJpg)).saveImageAsPng();

                        showSaveImageLayout(imagetosave);
                    }
                });



//                Bitmap imagetosave = new SaveImage(EditorActivity.this,findViewById(R.id.layoutArtboardPng)).saveImageAsPng();
//
//                showSaveImageLayout(imagetosave);
//                showSaveImageDialog(imagetosave);
//                saveImage();
            }
        });

        offlineBgsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invisibleList2();
                if(linearLayout_offlineBgs.getVisibility() == View.GONE)
                {
                    linearLayout_offlineBgs.setVisibility(View.VISIBLE);
                }
                offlineBgsWorking();
            }
        });


        gradientBgsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                invisibleAllLists();
                if(linearlayoutGradients.getVisibility() == View.GONE)
                {
                    linearlayoutGradients.setVisibility(View.VISIBLE);
                }
                gradientWorking();
            }
        });



        stickerLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(stickerLayout.getVisibility() == View.VISIBLE)
                {
                    stickerLayout.setVisibility(View.GONE);
                }
                stickerView.showIcons = false;
                stickerView.showBorder = false;
//                invisibleAllLists();
                stickerView.invalidate();
                return true;
            }
        });

        backArrowBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private void showSaveImageLayout(Bitmap imagetosave) {

        findViewById(R.id.savedImageShowLayout).setVisibility(View.VISIBLE);
        ImageView imageSaved = findViewById(R.id.imageShowSavedIv);
        imageSaved.setImageBitmap(imagetosave);
        TextView shareSavedImg = findViewById(R.id.shareSavedImg);

//        shareSavedImg.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                shareBitmapToOthers(imagetosave);
//            }
//        });

        ImageView savedImaged = findViewById(R.id.savedImageIv);
        RelativeLayout localAdlayout = findViewById(R.id.localAdLayout);
        ImageView adImg = findViewById(R.id.localAdIv);
        TextView adTitle = findViewById(R.id.localAdTitle);
        TextView adDesc = findViewById(R.id.localAdDesc);
        String appLink;

        int randomAd = randomLocalAd();
        if(randomAd == 1)
        {
            adImg.setImageResource(Data.App1Drawable);
            adTitle.setText(Data.App1Title);
            adDesc.setText(Data.App1Desc);
            appLink = Data.App1Link;
        }
        else if(randomAd == 2)
        {
            adImg.setImageResource(Data.App2Drawable);
            adTitle.setText(Data.App2Title);
            adDesc.setText(Data.App2Desc);
            appLink = Data.App2Link;
        }
        else if(randomAd == 3)
        {
            adImg.setImageResource(Data.App3Drawable);
            adTitle.setText(Data.App3Title);
            adDesc.setText(Data.App3Desc);
            appLink = Data.App3Link;
        }
        else if(randomAd == 4)
        {
            adImg.setImageResource(Data.App4Drawable);
            adTitle.setText(Data.App4Title);
            adDesc.setText(Data.App4Desc);
            appLink = Data.App4Link;
        }
        else if(randomAd == 5)
        {
            adImg.setImageResource(Data.App5Drawable);
            adTitle.setText(Data.App5Title);
            adDesc.setText(Data.App5Desc);
            appLink = Data.App5Link;
        }
        else if(randomAd == 6)
        {
            adImg.setImageResource(Data.App6Drawable);
            adTitle.setText(Data.App6Title);
            adDesc.setText(Data.App6Desc);
            appLink = Data.App6Link;
        }
        else
        {
            adImg.setImageResource(Data.App1Drawable);
            adTitle.setText(Data.App1Title);
            adDesc.setText(Data.App1Desc);
            appLink = Data.App1Link;
        }

        localAdlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreapp(appLink);
            }
        });

    }

    public void moreApps(View view)
    {
        Uri uri = Uri.parse("https://play.google.com/store/apps/developer?id=Quest%20Appx");
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    public void shareBitmapToOthers(Bitmap bitmap) {
        Intent i = new Intent(Intent.ACTION_SEND);

        i.setType("image/*");
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
    /*compress(Bitmap.CompressFormat.PNG, 100, stream);
    byte[] bytes = stream.toByteArray();*/

        i.putExtra(Intent.EXTRA_STREAM, bitmap);
        try {
            startActivity(Intent.createChooser(i, "My Profile ..."));
        } catch (android.content.ActivityNotFoundException ex) {
            ex.printStackTrace();
        }
    }

    public void ShareSaved(View view)
    {
         ArrayList<File> files;
        File file = new File(getFilesDir(), getResources().getString(R.string.fileName));

        if (!file.exists()) {
            file.mkdir();
        }


        files = new ArrayList<>();
        if (file.exists()) {
            for (File file1 : file.listFiles()) {
                files.add(file1);
            }
//            if (files != null) {
//                adapter = new SaveAdapter(files, this);
//                recyclerView.setAdapter(adapter);
//            }
        }

        int pos = files.size();
        SaveWork.pos = pos-1;
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.putExtra(Intent.EXTRA_TEXT, "Download App and Design more tshirts : "+"https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName());
        Uri uri = FileProvider.getUriForFile(EditorActivity.this, getApplicationContext().getPackageName() + ".provider", files.get(pos-1));
//        Uri uri = FileProvider.getUriForFile(EditorActivity.this, getApplicationContext().getPackageName() + ".provider", SaveWork.files.get(SaveWork.pos));
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
        shareIntent.setType("image/png");
        shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivity(Intent.createChooser(shareIntent, "send"));
    }

    public void DeleteSaved(View view)
    {
        new DeleteAlert(view.getContext()).textDialog();
    }

    private void showImportImageGalleryDialog() {
        Dialog dialog = new Dialog(EditorActivity.this);
        dialog.setContentView(R.layout.import_picture_dialog);
        dialog.show();

        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        TextView importAsBg = dialog.findViewById(R.id.importAsBackground);
        TextView importAsSticker = dialog.findViewById(R.id.importAsSticker);

        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                galleryBtn.setImageResource(R.drawable.gallery);
                int padding2 = dpToPx(15);
                galleryBtn.setPadding(padding2,padding2,padding2,padding2);
            }
        });

        importAsBg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhotoFromGallery(Data.PHOTO_GALLERY_FOR_BG);
                dialog.dismiss();
            }
        });

        importAsSticker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePhotoFromGallery(Data.PHOTO_GALLERY_FOR_STICKER);
                dialog.dismiss();
            }
        });
    }

    private void defaultMainButtonsNoClick()
    {
        int padding2 = dpToPx(15);
        framesBtn.setImageResource(R.drawable.bg);
        framesBtn.setPadding(padding2,padding2,padding2,padding2);
        galleryBtn.setImageResource(R.drawable.gallery);
        galleryBtn.setPadding(padding2,padding2,padding2,padding2);
        tshirtsBtn.setImageResource(R.drawable.tshirt);
        tshirtsBtn.setPadding(padding2,padding2,padding2,padding2);
        addStickerBtn.setImageResource(R.drawable.sticker);
        addStickerBtn.setPadding(padding2,padding2,padding2,padding2);
        textBtn.setImageResource(R.drawable.addtext);
        textBtn.setPadding(padding2,padding2,padding2,padding2);
        saveImageBtn.setImageResource(R.drawable.save);
        saveImageBtn.setPadding(padding2,padding2,padding2,padding2);
    }

    private void gradientWorking() {

        ImageView startcolor = findViewById(R.id.startColorGradient);
        ImageView endcolor = findViewById(R.id.endColorGradient);
        Button okGradientBtn = findViewById(R.id.okGradientBtn);

        startcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmbilWarnaDialog dialog = new AmbilWarnaDialog(EditorActivity.this, getResources().getColor(R.color.blue), true, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {

                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        startcolor.setBackgroundColor(color);
                        gradColor1 = color;
                    }
                });
                dialog.show();
            }
        });

        endcolor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmbilWarnaDialog dialog = new AmbilWarnaDialog(EditorActivity.this, getResources().getColor(R.color.blue), true, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {

                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        endcolor.setBackgroundColor(color);
                        gradColor2 = color;
                    }
                });
                dialog.show();
            }
        });

        RadioGroup radioGroup = findViewById(R.id.radioGroupGradient);

        okGradientBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedId = radioGroup.getCheckedRadioButtonId();
                if (selectedId == -1)
                {
                    Toast.makeText(EditorActivity.this, "Select Gradient Type.", Toast.LENGTH_SHORT).show();
                    return;
                }
                else {
                    RadioButton radioButton = (RadioButton) radioGroup.findViewById(selectedId);
                    if(radioButton.getId() == R.id.gradRadio1)
                    {
                        setLinearGradient(imageviewTshirt, gradColor1, gradColor2);
                    }
                    else
                    {
                        setRadialGradient(imageviewTshirt, gradColor1, gradColor2);
                    }
                }
            }
        });

    }

    private void setRadialGradient(ImageView imageview, int startcolor, int endcolor) {
        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setGradientType(GradientDrawable.RADIAL_GRADIENT);
        gradientDrawable.setShape(GradientDrawable.RECTANGLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            gradientDrawable.setColors(new int[]{
//                    getColor(startcolor),
//                    getColor(endcolor)
                    startcolor, endcolor
            });
        }
        gradientDrawable.setGradientRadius(400);
        imageview.setBackground(gradientDrawable);
        imageview.invalidate();
    }

    private void setLinearGradient(ImageView imageview, int startcolor, int endcolor) {
        //                        GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, new int[] {Color.RED, Color.BLUE});
//                        imageviewTshirt.setBackground(gradientDrawable);


        GradientDrawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setGradientType(GradientDrawable.LINEAR_GRADIENT);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            gradientDrawable.setColors(new int[]{
//                    getColor(startcolor),
//                    getColor(endcolor)
                    startcolor, endcolor
            });
        }
        gradientDrawable.setGradientRadius(400);
        imageview.setBackground(gradientDrawable);
        imageview.invalidate();
    }




    private void textWorkings() {
        layoutTextWorkingsLayout.setVisibility(View.VISIBLE);

        RelativeLayout editTextBtn = findViewById(R.id.editTextBtn);
        RelativeLayout changeColorBtn = findViewById(R.id.changeColorBtn);
        RelativeLayout shadowTextBtn = findViewById(R.id.shadowBtn);
        RelativeLayout changeFontBtn = findViewById(R.id.changeFontBtn);
        RelativeLayout changeTextOpacity = findViewById(R.id.changeTextOpacityBtn);
        RelativeLayout changeTextAlign = findViewById(R.id.changeTextAlignBtn);


        editTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                invisibleList2();
                if(stickerView.getCurrentSticker() instanceof TextSticker)
                {
                    editText();
                }
                else
                {
                    Toast.makeText(EditorActivity.this, "Select Text", Toast.LENGTH_SHORT).show();
                }
            }
        });

        changeColorBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invisibleList2();
                linearLayoutColors.setVisibility(View.VISIBLE);
                colorsWorking(Data.COLOR_FOR_TEXT);
            }
        });


        shadowTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invisibleList2();
                shadowWorkings();
                shadowAdjustmnetLayout.setVisibility(View.VISIBLE);
            }
        });

        changeTextAlign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invisibleList2();

                findViewById(R.id.linearlayout_textAlignment).setVisibility(View.VISIBLE);

                if(stickerView.getCurrentSticker() instanceof TextSticker)
                {
                    textAlignmentWorking();
                }
                else {
                    Toast.makeText(EditorActivity.this, "Select Text", Toast.LENGTH_SHORT).show();
                }
            }
        });

        changeFontBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invisibleList2();
                linearLayoutFonts.setVisibility(View.VISIBLE);
                fontWorking();
            }
        });

        changeTextOpacity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invisibleList2();
                linearLayoutTextOpacity.setVisibility(View.VISIBLE);
                textOpacityWorking();
            }
        });

    }

    private void textOpacityWorking() {
        SeekBar seekBarOpacity = findViewById(R.id.seekTextOpacity);
        seekBarOpacity.setMax(255);
        if(stickerView.getCurrentSticker() == null)
        {
            seekBarOpacity.setProgress(seekBarOpacity.getMax());
        }
         if(!(stickerView.getCurrentSticker() instanceof TextSticker))
        {
            seekBarOpacity.setProgress(seekBarOpacity.getMax());
        }
        else
        {
            seekBarOpacity.setProgress(((TextSticker)stickerView.getCurrentSticker()).getAlpha());
        }

        seekBarOpacity.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                try {
                    if(stickerView.getCurrentSticker() == null)
                    {
                        Toast.makeText(EditorActivity.this, "Please Select Text First", Toast.LENGTH_SHORT).show();
                    }
                    if (stickerView.getCurrentSticker() instanceof TextSticker) {
                        (stickerView.getCurrentSticker()).setAlpha(progress);
                        stickerView.invalidate();
                    } else {
                        Log.v("ss", "errr");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    private void shadowWorkings() {
        SeekBar seekBarDx = findViewById(R.id.seekTextShadowDX);
        seekBarDx.setProgress(textShadowDx);
        seekBarDx.setMax(50);

        SeekBar seekBarDy = findViewById(R.id.seekTextShadowDy);
        seekBarDy.setProgress(textShadowDy);
        seekBarDy.setMax(50);

        SeekBar seekBarRadius = findViewById(R.id.seekTextShadowRadius);
        seekBarRadius.setProgress(textShadowRadius);
        seekBarRadius.setMax(25);


        seekBarDx.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textShadowDx = progress;
                try {
                    if (stickerView.getCurrentSticker() instanceof TextSticker) {


                        ((TextSticker) stickerView.getCurrentSticker()).textPaint.setShadowLayer(textShadowRadius, textShadowDx, textShadowDy, textShadowColor);
                        stickerView.invalidate();
                    } else {
                        Toast.makeText(EditorActivity.this, "Select Text", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarDy.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textShadowDy = progress;
                try {
                    if (stickerView.getCurrentSticker() instanceof TextSticker) {

                        ((TextSticker) stickerView.getCurrentSticker()).textPaint.setShadowLayer(textShadowRadius, textShadowDx, textShadowDy, textShadowColor);
                        stickerView.invalidate();
                    } else {
                        Toast.makeText(EditorActivity.this, "Select Text", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        seekBarRadius.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                textShadowRadius = progress;
                try {
                    if (stickerView.getCurrentSticker() instanceof TextSticker) {

                        ((TextSticker) stickerView.getCurrentSticker()).textPaint.setShadowLayer(textShadowRadius, textShadowDx, textShadowDy, textShadowColor);
                        stickerView.invalidate();
                    } else {
                        Toast.makeText(EditorActivity.this, "Select Text", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        textShadowColorPicker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmbilWarnaDialog dialog = new AmbilWarnaDialog(EditorActivity.this, getResources().getColor(R.color.blue), true, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {

                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        try {
                            if (stickerView.getCurrentSticker() instanceof TextSticker) {
                                textShadowColor = color;
                                ((TextSticker) stickerView.getCurrentSticker()).textPaint.setShadowLayer(textShadowRadius, textShadowDx, textShadowDy, textShadowColor);
                                stickerView.invalidate();
                            } else {
                                Toast.makeText(EditorActivity.this, "Select Text", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                dialog.show();
            }
        });



    }

    private void showInterstitial() {
        if(interstitialAdImplement!=null)
        {
            interstitialAdImplement.showInterstitial();
        }
//        if(interstitialAd != null)
//        {
//            interstitialAd.show(EditorActivity.this);
//        }
    }

    private void shapesAdjustmentWorking() {
        linearlayoutStickerAdjustment.setVisibility(View.VISIBLE);

        TextView controlUp = findViewById(R.id.stickerControlUp);
        TextView controlBottom = findViewById(R.id.stickerControlBottom);
        TextView controlLeft = findViewById(R.id.stickerControlLeft);
        TextView controlRight = findViewById(R.id.stickerControlRight);



        controlUp.setOnTouchListener(new LongPressListener(new LongPressListener.OnLongPressListener() {
            @Override
            public void onLongPress() {
                Sticker sticker = stickerView.getCurrentSticker();
                if(sticker == null)
                {
                    Toast.makeText(EditorActivity.this, "Select Sticker", Toast.LENGTH_SHORT).show();
                    return;
                }
                float[] mappedBoundPoints = new float[8];
                Matrix matrix = sticker.getMatrix();
                matrix.mapPoints(mappedBoundPoints, sticker.getMappedBoundPoints());
                matrix.postTranslate(0, -5);
                sticker.setMatrix(matrix);
                stickerView.invalidate();
            }
        }));

        controlBottom.setOnTouchListener(new LongPressListener(new LongPressListener.OnLongPressListener() {
            @Override
            public void onLongPress() {
                Sticker sticker = stickerView.getCurrentSticker();
                if(sticker == null)
                {
                    Toast.makeText(EditorActivity.this, "Select Sticker", Toast.LENGTH_SHORT).show();
                    return;
                }
                float[] mappedBoundPoints = new float[8];
                Matrix matrix = sticker.getMatrix();
                matrix.mapPoints(mappedBoundPoints, sticker.getMappedBoundPoints());
                matrix.postTranslate(0, 5);
                sticker.setMatrix(matrix);
                stickerView.invalidate();
            }
        }));

        controlLeft.setOnTouchListener(new LongPressListener(new LongPressListener.OnLongPressListener() {
            @Override
            public void onLongPress() {
                Sticker sticker = stickerView.getCurrentSticker();
                if(sticker == null)
                {
                    Toast.makeText(EditorActivity.this, "Select Sticker", Toast.LENGTH_SHORT).show();
                    return;
                }
                float[] mappedBoundPoints = new float[8];
                Matrix matrix = sticker.getMatrix();
                matrix.mapPoints(mappedBoundPoints, sticker.getMappedBoundPoints());
                matrix.postTranslate(-5, 0);
                sticker.setMatrix(matrix);
                stickerView.invalidate();
            }
        }));

        controlRight.setOnTouchListener(new LongPressListener(new LongPressListener.OnLongPressListener() {
            @Override
            public void onLongPress() {
                Sticker sticker = stickerView.getCurrentSticker();
                if(sticker == null)
                {
                    Toast.makeText(EditorActivity.this, "Select Sticker", Toast.LENGTH_SHORT).show();
                    return;
                }
                float[] mappedBoundPoints = new float[8];
                Matrix matrix = sticker.getMatrix();
                matrix.mapPoints(mappedBoundPoints, sticker.getMappedBoundPoints());
                matrix.postTranslate(5, 0);
                sticker.setMatrix(matrix);
                stickerView.invalidate();
            }
        }));


        SeekBar stickerOpacitySeebar;
        ImageButton stickerColorPickerBtn;


        stickerOpacitySeebar = findViewById(R.id.seekbarStickerOpacity);
        stickerOpacitySeebar.setMax(255);
        stickerOpacitySeebar.setProgress(((DrawableSticker)stickerView.getCurrentSticker()).getAlpha());

        stickerOpacitySeebar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                try {
                    if (stickerView.getCurrentSticker() instanceof com.xiaopo.flying.logoSticker.DrawableSticker) {
                        ((com.xiaopo.flying.logoSticker.DrawableSticker) stickerView.getCurrentSticker()).setAlpha(progress);
                        stickerView.invalidate();
                    } else {
                        Log.v("ss", "errr");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        stickerColorPickerBtn = findViewById(R.id.stickerColorPickerBtn);

        stickerColorPickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AmbilWarnaDialog dialog = new AmbilWarnaDialog(EditorActivity.this, getResources().getColor(R.color.blue), true, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                    @Override
                    public void onCancel(AmbilWarnaDialog dialog) {

                    }

                    @Override
                    public void onOk(AmbilWarnaDialog dialog, int color) {
                        changeStickerColor(color);
                    }
                });
                dialog.show();
            }
        });

    }

    private void changeTshirtColor(int color) {
        Drawable tshirtdrawable = imageviewTshirt.getDrawable();

        if (tshirtdrawable != null) {
            tshirtdrawable = tshirtdrawable.mutate();

            ColorFilter colorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY);
            tshirtdrawable.setColorFilter(colorFilter);

            imageviewTshirt.setImageDrawable(tshirtdrawable);
        }
    }



    private void changeStickerColor(int color) {
//        changeTshirtColor(color);
        try {
            if (stickerView.getCurrentSticker() instanceof com.xiaopo.flying.logoSticker.DrawableSticker) {
                ColorFilter colorFilter = new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY);
                com.xiaopo.flying.logoSticker.DrawableSticker drawableSticker;
                Drawable mStickerDrawable = ((com.xiaopo.flying.logoSticker.DrawableSticker) stickerView.getCurrentSticker()).getDrawable().mutate();

                mStickerDrawable.setColorFilter(colorFilter);
                drawableSticker = (com.xiaopo.flying.logoSticker.DrawableSticker) stickerView.getCurrentSticker().setDrawable(mStickerDrawable);

                stickerView.replace(drawableSticker);
                stickerView.invalidate();
            } else {
                Toast.makeText(EditorActivity.this, "Select Text", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @SuppressLint("ClickableViewAccessibility")
    public void addTextDialog()
    {
        Dialog dialog = new Dialog(EditorActivity.this);
        dialog.setContentView(R.layout.changetext_dialo);
        dialog.show();
        Button buttonCancel = dialog.findViewById(R.id.dialogBtnCancel);
        Button buttonOk = dialog.findViewById(R.id.dialogBtnOk);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        EditText editTextDialog = dialog.findViewById(R.id.edittextDialog);

//        buttonOk.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                buttonOk.setBackgroundColor(ContextCompat.getColor(EditorActivity.this, R.color.red));
//                return false;
//            }
//        });


        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultMainButtonsNoClick();
                if (TextUtils.isEmpty(editTextDialog.getText().toString()))
                {
                    dialog.dismiss();
                    Toast.makeText(EditorActivity.this, "Please Enter text", Toast.LENGTH_SHORT).show();

                    return;
                }
                addText(editTextDialog.getText().toString());
                dialog.dismiss();
//                invisibleAllLists();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                defaultMainButtonsNoClick();
            }
        });

//        buttonCancel.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                buttonCancel.setBackgroundColor(ContextCompat.getColor(EditorActivity.this, R.color.red));
//                return false;
//            }
//        });


    }

    private void editText()
    {
        Dialog dialog = new Dialog(EditorActivity.this);
        dialog.setContentView(R.layout.changetext_dialo);
        dialog.show();
        Button buttonCancel = dialog.findViewById(R.id.dialogBtnCancel);
        Button buttonOk = dialog.findViewById(R.id.dialogBtnOk);
        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);
        EditText editTextDialog = dialog.findViewById(R.id.edittextDialog);
        TextSticker textSticker = (TextSticker) stickerView.getCurrentSticker();

        if(textSticker == null)
        {
            Toast.makeText(this, "No Sticker Selected", Toast.LENGTH_SHORT).show();
            return;
        }

        editTextDialog.setText(textSticker.getText());


        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textSticker == null)
                {
                    Toast.makeText(EditorActivity.this, "No Sticker Selected", Toast.LENGTH_SHORT).show();
                }

                if (TextUtils.isEmpty(editTextDialog.getText().toString()))
                {
                    dialog.dismiss();
                    Toast.makeText(EditorActivity.this, "Please Enter text", Toast.LENGTH_SHORT).show();
                    return;
                }

                textSticker.setText(editTextDialog.getText().toString());
                textSticker.resizeText();
                stickerView.invalidate();
                dialog.dismiss();
            }
        });
        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


    }

    @SuppressLint("ClickableViewAccessibility")
    private void textAlignmentWorking()
    {
        TextView leftAlign = findViewById(R.id.textAlignLeft_Btn);
        TextView  rightAlign = findViewById(R.id.textAlignRight_Btn);
        TextView centerAlign = findViewById(R.id.textAlignCenter_Btn);

        SeekBar linespacingSeek = findViewById(R.id.seekbarLetterspacing);

        TextSticker sticker = (TextSticker) stickerView.getCurrentSticker();
        if(sticker == null)
        {
            return;
        }
        if (!(sticker instanceof TextSticker))
        {
            Toast.makeText(this, "Select Text", Toast.LENGTH_SHORT).show();
            return;
        }



        linespacingSeek.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (sticker == null || !(sticker instanceof TextSticker)) {
                    return;
                }
                    float f = (float) progress/50;
                    sticker.setLetterSpacing(f);
                    stickerView.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        TextView controlLeft = findViewById(R.id.textControlLeft);
        TextView controlUp = findViewById(R.id.textControlUp);
        TextView controlRight = findViewById(R.id.textControlRight);
        TextView controlBottom = findViewById(R.id.textControlBottom);

        TextSticker textSticker = (TextSticker) stickerView.getCurrentSticker();
        if(textSticker == null)
        {
            return;
        }

        leftAlign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textSticker instanceof TextSticker)
                {
                    textSticker.setTextAlign(Layout.Alignment.ALIGN_NORMAL);
                    textSticker.resizeText();

                    stickerView.invalidate();
                }
            }
        });

        centerAlign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textSticker instanceof TextSticker)
                {
                    textSticker.setTextAlign(Layout.Alignment.ALIGN_CENTER);
                    textSticker.resizeText();

                    stickerView.invalidate();
                }
            }
        });

        rightAlign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(textSticker instanceof TextSticker)
                {
                    textSticker.setTextAlign(Layout.Alignment.ALIGN_OPPOSITE);
                    textSticker.resizeText();

                    stickerView.invalidate();
                }
            }
        });


        controlUp.setOnTouchListener(new LongPressListener(new LongPressListener.OnLongPressListener() {
            @Override
            public void onLongPress() {
                if(textSticker == null)
                {
                    return;
                }
                float[] mappedBoundPoints = new float[8];
                Matrix matrix = textSticker.getMatrix();
                matrix.mapPoints(mappedBoundPoints, textSticker.getMappedBoundPoints());
                matrix.postTranslate(0, -5);
                textSticker.setMatrix(matrix);
                stickerView.invalidate();
            }
        }));

        controlBottom.setOnTouchListener(new LongPressListener(new LongPressListener.OnLongPressListener() {
            @Override
            public void onLongPress() {
                if(textSticker == null)
                {
                    return;
                }
                float[] mappedBoundPoints = new float[8];
                Matrix matrix = textSticker.getMatrix();
                matrix.mapPoints(mappedBoundPoints, textSticker.getMappedBoundPoints());
                matrix.postTranslate(0, 5);
                textSticker.setMatrix(matrix);
                stickerView.invalidate();
            }
        }));

        controlLeft.setOnTouchListener(new LongPressListener(new LongPressListener.OnLongPressListener() {
            @Override
            public void onLongPress() {
                if(textSticker == null)
                {
                    return;
                }
                float[] mappedBoundPoints = new float[8];
                Matrix matrix = textSticker.getMatrix();
                matrix.mapPoints(mappedBoundPoints, textSticker.getMappedBoundPoints());
                matrix.postTranslate(-5, 0);
                textSticker.setMatrix(matrix);
                stickerView.invalidate();
            }
        }));


        controlRight.setOnTouchListener(new LongPressListener(new LongPressListener.OnLongPressListener() {
            @Override
            public void onLongPress() {
                if(textSticker == null)
                {
                    return;
                }
                float[] mappedBoundPoints = new float[8];
                Matrix matrix = textSticker.getMatrix();
                matrix.mapPoints(mappedBoundPoints, textSticker.getMappedBoundPoints());
                matrix.postTranslate(5, 0);
                textSticker.setMatrix(matrix);
                stickerView.invalidate();
            }
        }));

    }

    @SuppressLint("ResourceAsColor")
    private void addText(String name)
    {
        TextSticker textSticker = new TextSticker(this);
        textSticker.setText(name);

//        TextView text;
//        text.setRotation();





        textSticker.setTextColor(Color.WHITE);

        Typeface typeface = ResourcesCompat.getFont(EditorActivity.this, R.font.arlrdbd);
        textSticker.setTypeface(typeface);
        textSticker.setTextAlign(Layout.Alignment.ALIGN_CENTER);
        textSticker.resizeText();

        stickerView.addSticker(textSticker);

    }


    public void choosePhotoFromGallery(int importAs) {
        // Create the Intent for Image Gallery.
        Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        photoFromGallery = importAs;

        // Start new activity with the LOAD_IMAGE_RESULTS to handle back the results when image is picked from the Image Gallery.
        startActivityForResult(i, LOAD_IMAGE_RESULTS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Here we need to check if the activity that was triggers was the Image Gallery.
        // If it is the requestCode will match the LOAD_IMAGE_RESULTS value.
        // If the resultCode is RESULT_OK and there is some data we know that an image was picked.
        if (requestCode == LOAD_IMAGE_RESULTS && resultCode == RESULT_OK && data != null) {
            // Let's read picked image data - its URI
            pickedImage = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(pickedImage);
                Drawable drawable = Drawable.createFromStream(inputStream, pickedImage.toString());

                if(photoFromGallery == Data.PHOTO_GALLERY_FOR_STICKER)
                {
                    DrawableSticker sticker = new DrawableSticker(drawable);
                    stickerView.addSticker(sticker);
                    stickerView.invalidate();
                }
                else
                {
                    imageviewTshirt.setBackground(drawable);
                }
                //to change picture in filter layout
            } catch (Exception e) {
                Toast.makeText(EditorActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        galleryBtn.setImageResource(R.drawable.gallery);
        int padding2 = dpToPx(15);
        galleryBtn.setPadding(padding2,padding2,padding2,padding2);

//        invisibleAllLists();
    }

    private void showSaveImageDialog(Bitmap bitmap) {
        Dialog dialog = new Dialog(EditorActivity.this);
        dialog.setContentView(R.layout.imagesaved_dialog);
        dialog.show();
        TextView buttonOk = dialog.findViewById(R.id.dialogBtnSavedOk);
        ImageView savedImaged = dialog.findViewById(R.id.savedImageIv);
        RelativeLayout localAdlayout = dialog.findViewById(R.id.localAdLayout);
        ImageView adImg = dialog.findViewById(R.id.localAdIv);
        TextView adTitle = dialog.findViewById(R.id.localAdTitle);
        TextView adDesc = dialog.findViewById(R.id.localAdDesc);
        String appLink;

        int randomAd = randomLocalAd();
        if(randomAd == 1)
        {
            adImg.setImageResource(Data.App1Drawable);
            adTitle.setText(Data.App1Title);
            adDesc.setText(Data.App1Desc);
            appLink = Data.App1Link;
        }
        else if(randomAd == 2)
        {
            adImg.setImageResource(Data.App2Drawable);
            adTitle.setText(Data.App2Title);
            adDesc.setText(Data.App2Desc);
            appLink = Data.App2Link;
        }
        else if(randomAd == 3)
        {
            adImg.setImageResource(Data.App3Drawable);
            adTitle.setText(Data.App3Title);
            adDesc.setText(Data.App3Desc);
            appLink = Data.App3Link;
        }
        else if(randomAd == 4)
        {
            adImg.setImageResource(Data.App4Drawable);
            adTitle.setText(Data.App4Title);
            adDesc.setText(Data.App4Desc);
            appLink = Data.App4Link;
        }
        else if(randomAd == 5)
        {
            adImg.setImageResource(Data.App5Drawable);
            adTitle.setText(Data.App5Title);
            adDesc.setText(Data.App5Desc);
            appLink = Data.App5Link;
        }
        else if(randomAd == 6)
        {
            adImg.setImageResource(Data.App6Drawable);
            adTitle.setText(Data.App6Title);
            adDesc.setText(Data.App6Desc);
            appLink = Data.App6Link;
        }
        else
        {
            adImg.setImageResource(Data.App1Drawable);
            adTitle.setText(Data.App1Title);
            adDesc.setText(Data.App1Desc);
            appLink = Data.App1Link;
        }


        Drawable d = new BitmapDrawable(getResources(), bitmap);
        savedImaged.setImageDrawable(d);

        if (dialog.getWindow() != null)
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCancelable(false);



        buttonOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        localAdlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moreapp(appLink);
            }
        });

    }

    private void moreapp(String link)
    {
        Uri uri = Uri.parse(link);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    private int randomLocalAd()
    {
        Random rn = new Random();
        int answer = rn.nextInt(6) + 1;

        return answer;
    }


    public void saveImage() {
        if (ActivityCompat.checkSelfPermission(EditorActivity.this, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            Toast.makeText(EditorActivity.this, "You Should Grant Permission", Toast.LENGTH_LONG).show();
            if (Build.VERSION.SDK_INT >= 23) {
                EditorActivity.this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 1000);
            }
        }

        stickerView.showBorder = false;
        stickerView.showIcons = false;
        stickerView.invalidate();

        EditorActivity frameView = EditorActivity.this;
        new EditorActivity.ViewToImage(frameView, layout_artboard, new ActionListeners() {
            @Override
            public void convertedWithError(String str) {
                EditorActivity secondActivity = EditorActivity.this;
                Toast.makeText(secondActivity, "" + str, Toast.LENGTH_LONG).show();
            }

            @Override
            public void convertedWithSuccess(Bitmap bitmap, String str) {
                EditorActivity secondActivity = EditorActivity.this;
                Toast.makeText(secondActivity, "" + str, Toast.LENGTH_LONG).show();
            }
        });
    }

    public class ViewToImage {
        Bitmap bitmap = null;
        Context context;
        String fileName = "image";
        String filePath = null;
        String folderName = "DCIM";
        ActionListeners listeners;
        View view;

        public ViewToImage(Context context2, View view2, ActionListeners actionListeners) {
            this.context = context2;
            this.listeners = actionListeners;
            this.view = view2;
            convert();
        }

        private void convert() {
            View view2 = this.view;
            Bitmap bitmapFromView = getBitmapFromView(view2, view2.getWidth(), this.view.getHeight());
            if (this.fileName.equals("image")) {
                saveTheImage(bitmapFromView, (String) null);
            } else {
                saveTheImage(bitmapFromView, this.fileName);
            }
        }

        private Bitmap getBitmapFromView(View view2, int i, int i2) {
            Bitmap createBitmap = Bitmap.createBitmap(view2.getWidth(), view2.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(createBitmap);
            Drawable background = view2.getBackground();
            if (background != null) {
                background.draw(canvas);
            } else {
                canvas.drawColor(ViewCompat.MEASURED_STATE_MASK);
            }
            view2.draw(canvas);
            return createBitmap;
        }

        //        private void saveTheImage(Bitmap bitmap2, String str) {
        //            String str2;
        //            File file = new File(Environment.getExternalStorageDirectory().toString() + "/" + this.folderName);
        //            if (!file.exists()) {
        //                file.mkdirs();
        //            }
        //            int nextInt = new Random().nextInt(10000);
        //            if (str == null) {
        //                str2 = "image-" + nextInt + ".jpg";
        //            } else {
        //                str2 = str + ".jpg";
        //            }
        //            File file2 = new File(file, str2);
        //            this.context.sendBroadcast(new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE", Uri.fromFile(file2)));
        //            if (file2.exists()) {
        //                file2.delete();
        //            }
        //            try {
        //                FileOutputStream fileOutputStream = new FileOutputStream(file2);
        //                bitmap2.compress(Bitmap.CompressFormat.JPEG, 90, fileOutputStream);
        //                fileOutputStream.flush();
        //                fileOutputStream.close();
        ////                String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "Title", null);
        //                imagersaverInt++;
        //                if(imagersaverInt == 1)
        //                {
        //                    File imageFile = new File(Environment.getExternalStorageDirectory().toString() + "/" + this.folderName + "/" + str2);
        //                    if (imageFile.exists())
        //                    {
        //                        Toast.makeText(this.context, "Image is Saved in /" + this.folderName + " Folder in your Device!", Toast.LENGTH_LONG).show();
        //                    }
        //                }
        //                else if(imagersaverInt == 2)
        //                {
        //                    imagersaverInt = 0;
        //                    saveTheImage(bitmap2, str);
        //                }
        //                this.filePath = str2;
        //                ActionListeners actionListeners = this.listeners;
        //                if (actionListeners != null) {
        //                    actionListeners.convertedWithSuccess(this.bitmap, str2);
        //                }
        //            } catch (Exception e) {
        //                e.printStackTrace();
        //                ActionListeners actionListeners2 = this.listeners;
        //                if (actionListeners2 != null) {
        //                    actionListeners2.convertedWithError(e.getMessage());
        //                }
        //            }
        //        }

        private void saveTheImage(Bitmap bitmap2, String str) {
            String str2;

            int nextInt = new Random().nextInt(10000);
            if (str == null) {
                str2 = "image-" + nextInt + ".jpg";
            } else {
                str2 = str + ".jpg";
            }

            try {
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap2.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                String path = MediaStore.Images.Media.insertImage(getApplicationContext().getContentResolver(), bitmap2, str2, null);
                Toast.makeText(EditorActivity.this, "Image Saved!", Toast.LENGTH_SHORT).show();
            } catch (Exception e) {
                e.printStackTrace();
                ActionListeners actionListeners2 = this.listeners;
                if (actionListeners2 != null) {
                    actionListeners2.convertedWithError(e.getMessage());
                }
            }
        }
    }

    public void onBackPressed() {
        if(findViewById(R.id.savedImageShowLayout).getVisibility() == View.VISIBLE)
        {
            findViewById(R.id.savedImageShowLayout).setVisibility(View.GONE);
            return;
        }
        AlertDialog diaBox = AskOption();
        diaBox.show();
    }

    private AlertDialog AskOption()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                .setTitle("Alert")
                .setMessage("Changes may not be saved. Are you sure you want to exit?")

                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        stickerView.removeAllStickers();
                        finish();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;

    }

    private void offlineBgsWorking()
    {
        if(findViewById(R.id.recyclerview_colorsTshirt).getVisibility() == View.VISIBLE)
        {
            findViewById(R.id.recyclerview_colorsTshirt).setVisibility(View.GONE);
        }
//        LinearLayoutManager linearLayoutManagerframes = new LinearLayoutManager(EditorActivity.this, LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView_frames = findViewById(R.id.recyclerview_frames);
        findViewById(R.id.recyclerview_categoriesShirts).setVisibility(View.GONE);
        recyclerView_frames.setLayoutManager(new GridLayoutManager(EditorActivity.this, 5 , RecyclerView.VERTICAL, false));
//        recyclerView_frames.setHasFixedSize(true);


//        FrameListener frameListener = null;
        bgsAdapter = new FrameAdapter(EditorActivity.this, bgsLinks, BG_ADAPTER, new FrameListener() {
            @Override
            public void OnClick(int position) {
                Glide.with(EditorActivity.this)
                        .asDrawable()
                        .load(bgsAdapter.list.get(position))
                        .into(new SimpleTarget<Drawable>() {
                            @Override
                            public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                                imageviewTshirt.setBackground(resource);
                            }
                        });
            }
        });
//        bgsAdapter.setListener(frameListener);
        bgsAdapter.setProDataListener(new ProDataListener() {
            @Override
            public void onClick(int position) {
                String drawableId = bgsAdapter.list.get(position);
                watchAdToUnlockShirt(drawableId);

            }
        });
        recyclerView_frames.setAdapter(bgsAdapter);


    }


    private void tshirtsWorking()
    {
//        LinearLayoutManager linearLayoutManagerframes = new LinearLayoutManager(EditorActivity.this, LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerViewColorsForShirts = findViewById(R.id.recyclerview_colorsTshirt);
        if(recyclerViewColorsForShirts.getVisibility() == View.GONE)
        {
            recyclerViewColorsForShirts.setVisibility(View.VISIBLE);
        }
        RecyclerView categoriesShirtsRV = findViewById(R.id.recyclerview_categoriesShirts);
        categoriesShirtsRV.setVisibility(View.VISIBLE);
//
        recyclerViewColorsForShirts.setLayoutManager(new LinearLayoutManager(EditorActivity.this, RecyclerView.HORIZONTAL, false));
//        recyclerViewColorsForShirts.setHasFixedSize(true);
        ColorAdapter adapter_colorsTshirt = new ColorAdapter(EditorActivity.this, colorIDs,  new RecyclerListener() {
            @Override
            public void OnClick(int position) {
                if(position == 0)
                {
                    AmbilWarnaDialog dialog = new AmbilWarnaDialog(EditorActivity.this, getResources().getColor(R.color.black), true, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                        @Override
                        public void onCancel(AmbilWarnaDialog dialog) {

                        }

                        @Override
                        public void onOk(AmbilWarnaDialog dialog, int color) {
                            changeTshirtColor(color);
                        }
                    });dialog.show();
                }
                else if(position == 1)
                {
                    changeTshirtColor(Color.WHITE);
                }
                else
                {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        changeTshirtColor(getColor(colorIDs[position]));
                    }
                    else
                    {
                        changeTshirtColor(colorIDs[position]);
                    }
                }
            }
        });
        recyclerViewColorsForShirts.setAdapter(adapter_colorsTshirt);


        RecyclerView recyclerView_frames = findViewById(R.id.recyclerview_frames);
//        recyclerView_frames.setLayoutManager(linearLayoutManagerframes);
        recyclerView_frames.setLayoutManager(new GridLayoutManager(EditorActivity.this, 4, RecyclerView.VERTICAL, false));
//        recyclerView_frames.setHasFixedSize(false);


        shirtAdapter = new FrameAdapter(EditorActivity.this, tshirtsLinks, SHIRT_ADAPTER, new FrameListener() {
            @Override
            public void OnClick(int position) {
                Glide.with(EditorActivity.this).load(shirtAdapter.list.get(position)).into(imageviewTshirt);
            }


        });
        shirtAdapter.setProDataListener(new ProDataListener() {
            @Override
            public void onClick(int position) {
                String drawableId = shirtAdapter.list.get(position);
//                watchAdToUnlockShirt(drawableId);
//                loadAdmobRewarded(drawableId);
                watchAdToUnlockShirt(drawableId);

            }
        });
        recyclerView_frames.setAdapter(shirtAdapter);

    }

    private void colorsWorking(int colorFor)
    {
//        LinearLayoutManager linearLayoutManageColors = new LinearLayoutManager(EditorActivity.this, LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView_colors = findViewById(R.id.recyclerview_colors);
//        recyclerView_colors.setLayoutManager(linearLayoutManageColors);
        recyclerView_colors.setLayoutManager(new StaggeredGridLayoutManager( 6, StaggeredGridLayoutManager.VERTICAL));
//        recyclerView_colors.setHasFixedSize(true);

        ImageButton colorPickerBtn = findViewById(R.id.textColorPickerBtn);
        colorPickerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        RecyclerAdapter adapter_colors = new RecyclerAdapter(EditorActivity.this, colorIDs, 2, new RecyclerListener() {
            @Override
            public void OnClick(int position) {

                if(position == 0)
                {
                    AmbilWarnaDialog dialog = new AmbilWarnaDialog(EditorActivity.this, getResources().getColor(R.color.black), true, new AmbilWarnaDialog.OnAmbilWarnaListener() {
                        @Override
                        public void onCancel(AmbilWarnaDialog dialog) {

                        }

                        @Override
                        public void onOk(AmbilWarnaDialog dialog, int color) {
                            if(colorFor == Data.COLOR_FOR_TEXT)
                            {
                                try {
                                    if (stickerView.getCurrentSticker()instanceof TextSticker)
                                    {
                                        ((TextSticker)stickerView.getCurrentSticker()).setTextColor(color);
                                        stickerView.invalidate();
                                    }
                                    else
                                    {
                                        Toast.makeText(EditorActivity.this, "Select Text", Toast.LENGTH_SHORT).show();
                                    }
                                }catch (Exception e)
                                {
                                    e.printStackTrace();
                                }
                            }
                            else if(colorFor == Data.COLOR_FOR_BGS)
                            {
//                            imageviewBg.setBackgroundColor(ContextCompat.getColor(EditorActivity.this, color));
                                imageviewTshirt.setBackgroundColor(color);
                            }

                        }
                    });dialog.show();
                }
                else
                {
                    if(colorFor == Data.COLOR_FOR_TEXT)
                    {
                        if(stickerView.getCurrentSticker() == null)
                        {
                            Toast.makeText(EditorActivity.this, "Please select any text first", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if(!(stickerView.getCurrentSticker() instanceof TextSticker))
                        {
                            Toast.makeText(EditorActivity.this, "Please select any text first", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        TextSticker sticker = (TextSticker) stickerView.getCurrentSticker();

                        sticker.setTextColor(ContextCompat.getColor(EditorActivity.this, colorIDs[position]));
                        stickerView.invalidate();
                    }
                    else if(colorFor == Data.COLOR_FOR_BGS)
                    {
                        imageviewTshirt.setBackgroundColor(ContextCompat.getColor(EditorActivity.this, colorIDs[position]));
                        imageviewTshirt.invalidate();
                    }
                }



//                if(colorFor == Data.COLOR_FOR_TEXT)
//                {
//                    TextSticker sticker = (TextSticker) stickerView.getCurrentSticker();
//                    if(sticker == null)
//                    {
//                        Toast.makeText(EditorActivity.this, "Please select any text first", Toast.LENGTH_SHORT).show();
//                        return;
//                    }
//                    sticker.setTextColor(ContextCompat.getColor(EditorActivity.this, colorIDs[position]));
//                    stickerView.invalidate();
//                }
//                else if(colorFor == Data.COLOR_FOR_BGS)
//                {
//                    imageviewTshirt.setBackgroundColor(ContextCompat.getColor(EditorActivity.this, colorIDs[position]));
//                    imageviewTshirt.invalidate();
//                }

//                changeColorTv.setTextColor(ContextCompat.getColor(EditorActivity.this, R.color.lightblack));
//                changeColorImgBtn.setBackgroundTintList(ContextCompat.getColorStateList(EditorActivity.this, R.color.lightblack));
            }
        });
        recyclerView_colors.setAdapter(adapter_colors);
    }

    private void fontWorking()
    {
//        LinearLayoutManager linearLayoutManagerfonts = new LinearLayoutManager(EditorActivity.this, LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView_fonts = findViewById(R.id.recyclerview_fonts);
        recyclerView_fonts.setLayoutManager(new GridLayoutManager(EditorActivity.this, 4, RecyclerView.VERTICAL, false));
//        recyclerView_fonts.setHasFixedSize(true);
        FontAdapter adapter_fonts = new FontAdapter(EditorActivity.this, fontsIDs, new RecyclerListener() {
            @Override
            public void OnClick(int position) {
                if(stickerView.getCurrentSticker() == null)
                {
                    Toast.makeText(EditorActivity.this, "Please select any text first", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(!(stickerView.getCurrentSticker() instanceof TextSticker))
                {
                    Toast.makeText(EditorActivity.this, "Please select any text first", Toast.LENGTH_SHORT).show();
                    return;
                }
                TextSticker sticker = (TextSticker) stickerView.getCurrentSticker();

                Typeface typeface = ResourcesCompat.getFont(EditorActivity.this, fontsIDs[position]);
                sticker.setTypeface(typeface);
                stickerView.invalidate();

            }
        });
        recyclerView_fonts.setAdapter(adapter_fonts);
    }



    private void filtersWorking()
    {
//        LinearLayoutManager linearLayoutManagerfilters = new LinearLayoutManager(EditorActivity.this, LinearLayoutManager.HORIZONTAL,false);
        recyclerView_filters.setLayoutManager(new GridLayoutManager(EditorActivity.this, 6 , RecyclerView.VERTICAL, false));
//        recyclerView_filters.setHasFixedSize(true);
        adapter_filters = new RecyclerAdapter(EditorActivity.this, filterIDs, 4, new RecyclerListener() {
            @Override
            public void OnClick(int position) {
                imageview_filter.setImageResource(filterIDs[position]);
            }
        });
        recyclerView_filters.setAdapter(adapter_filters);

        SeekBar seekBarFilters = findViewById(R.id.filterSeekOpacity);
        seekBarFilters.setProgress(255);
        seekBarFilters.setMax(255);
        seekBarFilters.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                imageview_filter.setAlpha(progress);
                imageview_filter.invalidate();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void stickersWorking()
    {
//        LinearLayoutManager linearLayoutManagerStickers = new LinearLayoutManager(EditorActivity.this, LinearLayoutManager.HORIZONTAL,false);

        RecyclerView recyclerView_stickers = findViewById(R.id.recyclerview_stickers);
        recyclerView_stickers.setLayoutManager(new GridLayoutManager(EditorActivity.this, 5, RecyclerView.VERTICAL , false));
//        recyclerView_stickers.setHasFixedSize(true);
        RecyclerAdapter adapter_stickers = new RecyclerAdapter(EditorActivity.this, stickersIDs[0], 5, new RecyclerListener() {
            @Override
            public void OnClick(int position) {
                Drawable drawable = ContextCompat.getDrawable(EditorActivity.this, stickersIDs[0][position]);
                com.xiaopo.flying.logoSticker.DrawableSticker drawableSticker = new com.xiaopo.flying.logoSticker.DrawableSticker(drawable);
                stickerView.addSticker(drawableSticker);
                stickerView.invalidate();
            }
        });
        recyclerView_stickers.setAdapter(adapter_stickers);

        RecyclerView recyclerView_stickerCategories = findViewById(R.id.recyclerview_stickersCategories);
        if(stickerCategoryNames.length <= 1)
        {
            recyclerView_stickerCategories.setVisibility(View.GONE);
        }
        else
        {
            recyclerView_stickerCategories.setVisibility(View.VISIBLE);
            CategoryAdapter adapter = new CategoryAdapter(EditorActivity.this, stickerCategoryNames, new CategoryAdapter.CategoryListener() {
                @Override
                public void onItemSelected(int position) {
                    RecyclerAdapter adapter_stickers = new RecyclerAdapter(EditorActivity.this, stickersIDs[position], 5, new RecyclerListener() {
                        @Override
                        public void OnClick(int pos) {
                            Drawable drawable = ContextCompat.getDrawable(EditorActivity.this, stickersIDs[position][pos]);
                            com.xiaopo.flying.logoSticker.DrawableSticker drawableSticker = new com.xiaopo.flying.logoSticker.DrawableSticker(drawable);
                            stickerView.addSticker(drawableSticker);
                            stickerView.invalidate();
                        }
                    });

                    recyclerView_stickers.setAdapter(adapter_stickers);
                    adapter_stickers.notifyDataSetChanged();
                }
            });
            recyclerView_stickerCategories.setLayoutManager(new LinearLayoutManager(EditorActivity.this, LinearLayoutManager.HORIZONTAL, false));
            recyclerView_stickerCategories.setAdapter(adapter);
        };
    }

    public void invisibleList1(View view)
    {
        invisibleList1();
        invisibleList2();
    }

    private void func_shareapp()
    {
        try {
            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, R.string.app_name);
            String shareMessage= "\nLet your Friends know that you are using this app... Install now from link below:\n\n";
            String appLink = "https://play.google.com/store/apps/details?id=" + getPackageName();
            shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage+appLink);
            startActivity(Intent.createChooser(shareIntent, "choose one"));
        } catch(Exception e)
        {
            e.printStackTrace();
        }
    }

    private void func_NewYearEditor()
    {
        Uri uri = Uri.parse(Data.NewYearEditorLink);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    int dpToPx(int dp)
    {
        final float scale = getResources().getDisplayMetrics().density;
        int padding_in_px = (int) (dp * scale + 0.5f);
        return padding_in_px;
    }

    public void RateUs(View view)
    {
        String appLink = "https://play.google.com/store/apps/details?id=" + getPackageName();
        Uri uri = Uri.parse(appLink);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }


}
