package com.test.shaddditest.customview;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.test.shaddditest.json.User;

import java.util.List;

public class UserDiff extends DiffUtil.Callback {

    private final List<User> oldList;
    private final List<User> newList;

    public UserDiff(List<User> oldUserList, List<User> newUserList) {
        this.oldList = oldUserList;
        this.newList = newUserList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).getId() == newList.get(
                newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final User oldEmployee = oldList.get(oldItemPosition);
        final User newEmployee = newList.get(newItemPosition);

        return oldEmployee.getName().equals(newEmployee.getName());
    }

    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }
}
