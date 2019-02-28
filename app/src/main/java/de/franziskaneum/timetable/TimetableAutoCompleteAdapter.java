package de.franziskaneum.timetable;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;

import de.franziskaneum.R;
import de.franziskaneum.teacher.TeacherList;

/**
 * Created by Niko on 09.08.2017.
 */

public class TimetableAutoCompleteAdapter extends ArrayAdapter<TeacherList.TeacherData> {
    private TeacherList teacherList;
    private TeacherFilter filter;
    private int resourceId;

    public TimetableAutoCompleteAdapter(@NonNull Context context, @LayoutRes int resource, TeacherList teacherList) {
        super(context, resource, new TeacherList(teacherList));
        this.resourceId = resource;
        this.teacherList = teacherList;
        filter = new TeacherFilter();
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(resourceId, parent, false);
        }

        TeacherList.TeacherData teacher = getItem(position);
        if (teacher != null) {
            TextView teacherName = (TextView) convertView.findViewById(R.id.teacher_item_name);
            TextView teacherSubjects = (TextView) convertView.findViewById(R.id.teacher_item_subjects);
            ImageView teacherImage = (ImageView) convertView.findViewById(R.id.teacher_item_image);

            teacherName.setText(teacher.getForename() + " " + teacher.getName());

            String subjects = null;
            if (teacher.getSubjects() != null)
                for (String subject : teacher.getSubjects()) {
                    if (subjects == null) {
                        subjects = subject;
                    } else
                        subjects += ", " + subject;
                }

            teacherSubjects.setText(subjects);

            int textSize = (int) (18.0f * getContext().getResources().getDisplayMetrics().density);

            TextDrawable textDrawable = TextDrawable
                    .builder()
                    .beginConfig()
                    .bold()
                    .fontSize(textSize)
                    .endConfig()
                    .buildRound(
                            teacher.getShortcut(), teacher.getColor());
            teacherImage.setImageDrawable(textDrawable);
        }

        convertView.setClickable(false);

        return convertView;
    }

    @NonNull
    @Override
    public Filter getFilter() {
        return filter;
    }

    private class TeacherFilter extends Filter {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            TeacherList.TeacherData teacherData = (TeacherList.TeacherData) resultValue;
            return teacherData.getName();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null) {
                TeacherList filtered = new TeacherList();

                for (TeacherList.TeacherData teacher : teacherList) {
                    String teacherString = teacher.getForename() + " " + teacher.getName();

                    teacherString = teacherString.toLowerCase();
                    String[] keyWords = constraint.toString().toLowerCase().split(" ");
                    boolean contains = true;

                    for (String keyWord : keyWords) {
                        if (!teacherString.contains(keyWord)) {
                            contains = false;
                            break;
                        }
                    }

                    if (contains)
                        filtered.add(teacher);
                }

                results.values = filtered;
                results.count = filtered.size();
            } else {
                return new FilterResults();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if (results.values instanceof TeacherList) {
                TeacherList filteredTeacherList = (TeacherList) results.values;
                if (!filteredTeacherList.isEmpty()) {
                    clear();
                    addAll(filteredTeacherList);
                    notifyDataSetChanged();
                }
            }
        }

    }
}
