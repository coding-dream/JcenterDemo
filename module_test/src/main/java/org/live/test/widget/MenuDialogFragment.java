package org.live.test.widget;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import org.jetbrains.anko.ToastsKt;
import org.live.test.R;

import butterknife.ButterKnife;

/**
 * Created by wl on 2018/11/23.
 */
public class MenuDialogFragment extends DialogFragment implements View.OnClickListener {

    private static final String ARG_KEY_HOST_USER_NAME = "arg_key_user_name";
    private static String TAG = MenuDialogFragment.class.getSimpleName();

    TextView tvEdit;
    TextView tvDelete;

    private String userName;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parseParams(getArguments());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.zz_menu_dialog, null);
        ButterKnife.bind(this, rootView);
        initViews(rootView);
        return rootView;
    }

    private void parseParams(Bundle arguments) {
        userName = arguments.getString(ARG_KEY_HOST_USER_NAME, "");
    }

    public static void display(FragmentManager fm, String userName) {
        Fragment fragment = fm.findFragmentByTag(TAG);

        if (fragment != null && !fragment.isRemoving()) {
            fm.beginTransaction().remove(fragment).commitAllowingStateLoss();
        }
        MenuDialogFragment dialog = new MenuDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(ARG_KEY_HOST_USER_NAME, userName);
        dialog.setArguments(bundle);
        fm.beginTransaction().add(dialog, TAG).commitAllowingStateLoss();
    }

    /**
     * 反例: 不能重复添加同一个fragment实例
     * @param fm
     * @param userName
     * @param menuDialogFragment
     */
    public static void displayError(FragmentManager fm, String userName, MenuDialogFragment menuDialogFragment) {
        // 同一个fragment不能多次添加. Fragment already added: MenuDialogFragment{2b5c20f #0 MenuDialogFragment}
        Bundle bundle = new Bundle();
        bundle.putString(ARG_KEY_HOST_USER_NAME, userName);
        menuDialogFragment.setArguments(bundle);
        fm.beginTransaction().add(menuDialogFragment, TAG).commitAllowingStateLoss();
    }

    private void initViews(View rootView) {
        tvEdit = rootView.findViewById(R.id.tv_menu_edit);
        tvDelete = rootView.findViewById(R.id.tv_menu_delete);

        tvEdit.setOnClickListener(this);
        tvDelete.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        dismissAllowingStateLoss();
    }

    @Override
    public void onStart() {
        super.onStart();
        adjustDialogWindow();
    }

    private void adjustDialogWindow() {
        handleInLanscape();
        handleBaseWindow();
    }

    private void handleBaseWindow() {
        Window window = getDialog().getWindow();
        if (window != null) {
            setStyle(DialogFragment.STYLE_NO_FRAME, 0);
            window.getAttributes().windowAnimations = R.style.dialogAnim;
            window.setGravity(Gravity.BOTTOM);
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            WindowManager.LayoutParams params = window.getAttributes();
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(params);
        }
    }

    private void handleInLanscape() {
        // fix坑点(华为): 屏幕上有虚拟键盘时会莫名弹出导航栏不隐藏而导致 宝箱被遮挡,焦点抢占
        this.getDialog().getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE, WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            this.getDialog().getWindow().getDecorView().setSystemUiVisibility(uiOptions);
        }
        this.getDialog().getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_menu_edit:
                ToastsKt.toast(getActivity(), "edit");
                break;
            case R.id.tv_menu_delete:
                ToastsKt.toast(getActivity(), "delete");
                break;
            default:
                break;
        }
    }

    /**
     * 避免用户错误调用导致的某些问题
     */
    @Override
    public void dismiss() {
        dismissAllowingStateLoss();
    }
}
