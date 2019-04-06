/*
 *  Copyright (c) 2017.  Joe
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *
 */

package com.lovejjfg.arsenal.ui

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import com.lovejjfg.arsenal.R

/**
 * Created by Joe on 2017/2/21.
 * Email lovejjfg@gmail.com
 */

class LoadingDialog : DialogFragment() {
    private var isShow: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(android.support.v4.app.DialogFragment.STYLE_NO_TITLE, R.style.AppTheme_AppCompat_Dialog_Alert)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return super.onCreateDialog(savedInstanceState).apply {
            setCanceledOnTouchOutside(false)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.dialog_loading, container, false)
    }

    fun show(manager: FragmentManager) {
        if (!isAdded && !isShow) {
            isShow = true
            val dialog = manager.findFragmentByTag(this::class.java.simpleName)
            if (dialog == null) {
                val beginTransaction = manager.beginTransaction()
                beginTransaction.remove(this)
                beginTransaction.add(this, this::class.java.simpleName)
                beginTransaction.commitAllowingStateLoss()
            }
        }
    }

    override fun show(manager: FragmentManager?, tag: String?) {
        manager?.let {
            show(manager)
        }
    }

    override fun onCancel(dialog: DialogInterface?) {
        super.onCancel(dialog)
        dismiss()
    }

    override fun onDetach() {
        super.onDetach()
        dismiss()
    }

    override fun dismiss() {
        isShow = false
        dismissAllowingStateLoss()
    }

    override fun dismissAllowingStateLoss() {
        if (activity != null) {
            activity?.runOnUiThread {
                super.dismissAllowingStateLoss()
            }
        } else {
            super.dismissAllowingStateLoss()
        }
    }
}
