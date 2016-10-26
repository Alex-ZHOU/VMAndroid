/*
 * Copyright 2016 Alex_ZHOU
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 *
 * Copyright 2016 TomeOkin
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.alex.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import static android.support.v4.app.FragmentManager.POP_BACK_STACK_INCLUSIVE;

public class FragmentNavigator {

    public static final int REQUEST_CODE = 72;


    public static void moveTo(FragmentManager fragmentManager, String tag) {

    }

    public static void moveTo(FragmentManager fragmentManager, @NonNull Fragment fragment,
                              @IdRes int containerId, boolean addToBackStack) {

        Fragment current = fragmentManager.findFragmentById(containerId);

        if (current == fragment) {
            return;
        }

        final String tag = fragment.getTag();

        FragmentTransaction transaction = fragmentManager.beginTransaction();


        if (current == null) {
            transaction.add(containerId, fragment, tag);
        } else {
            transaction.replace(containerId, fragment, tag);
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            if (addToBackStack) {
                transaction.addToBackStack(tag);
            }
        }
        transaction.commit();

//        if (fragment == null) {
//            try {
//                target = Fragment.instantiate(context, fragment.getName(), args);
//            } catch (Exception e) {
//                // ignore
//            }
//            if (target == null) {
//                return;
//            }
//
//
//        } else {
//            if (current == target) {
//                return;
//            }
//            // set result
//            Intent intent = new Intent();
//            if (args != null) {
//                intent.putExtras(args);
//            }
//            target.onActivityResult(REQUEST_CODE, Activity.RESULT_OK, intent);
//            boolean result = fragmentManager.popBackStackImmediate(tag, 0);
//            if (!result) {
//                fragmentManager.popBackStackImmediate(0, POP_BACK_STACK_INCLUSIVE);
//            }
//        }

    }

    public static void moveTo(FragmentManager fragmentManager, @IdRes int containerId, Class<? extends Fragment> fragment,
                              boolean addToBackStack, Context context, @Nullable Bundle args) {
        final String tag = fragment.getName();
        Fragment current = fragmentManager.findFragmentById(containerId);
        Fragment target = fragmentManager.findFragmentByTag(tag);

        if (target == null) {
            try {
                target = Fragment.instantiate(context, fragment.getName(), args);
            } catch (Exception e) {
                // ignore
            }
            if (target == null) {
                return;
            }

            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (current == null) {
                transaction.add(containerId, target, tag);
            } else {
                transaction.replace(containerId, target, tag);
                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                if (addToBackStack) {
                    transaction.addToBackStack(tag);
                }
            }
            transaction.commit();
        } else {
            if (current == target) {
                return;
            }
            // set result
            Intent intent = new Intent();
            if (args != null) {
                intent.putExtras(args);
            }
            target.onActivityResult(REQUEST_CODE, Activity.RESULT_OK, intent);
            boolean result = fragmentManager.popBackStackImmediate(tag, 0);
            if (!result) {
                fragmentManager.popBackStackImmediate(0, POP_BACK_STACK_INCLUSIVE);
            }
        }
    }
}
