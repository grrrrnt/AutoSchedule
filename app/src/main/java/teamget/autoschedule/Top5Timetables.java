package teamget.autoschedule;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import teamget.autoschedule.mods.Module;
import teamget.autoschedule.mods.SampleModules;
import teamget.autoschedule.schedule.Priority;
import teamget.autoschedule.schedule.Timetable;
import teamget.autoschedule.schedule.TimetableGeneration;
import teamget.autoschedule.schedule.TimetableScoring;

public class Top5Timetables extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top5_timetables);

        // Receive SP for modules and priorities
        SharedPreferences modulePrefs = getSharedPreferences("ModulePreferences", MODE_PRIVATE);
        Set<String> moduleSet = modulePrefs.getStringSet("modules", null);
        List<String> moduleCodes = new ArrayList<String>(moduleSet);
        List<Module> modules = new ArrayList<>();
        for (String s : moduleCodes) {
            modules.add(SampleModules.getModuleByCode(s));
        }

        SharedPreferences priorityPrefs = getSharedPreferences("PriorityPreferences", MODE_PRIVATE);
        Set<String> prioritySet = priorityPrefs.getStringSet("priorities", null);
        List<String> moduleJson = new ArrayList<String>(prioritySet);
        List<Priority> priorities = new ArrayList<>();

        final Gson gson = new Gson();
        for (String s : moduleJson) {
            Priority p = gson.fromJson(s, Priority.class);
            priorities.add(p);
            // For testing
            Log.d("priority check", "priority rank: " + p.rank);
        }

        // For testing
        for (String s : moduleCodes) { Log.d("module check", "module: " + s); }

        // Generate list of timetables
        TimetableGeneration tg = new TimetableGeneration();
        tg.setModules(modules);
        List<Timetable> timetables = tg.getListOfTimetables();

        // Calculate score, tag score, arrange in decreasing order
        TimetableScoring.arrangeTimetablesByScore(timetables);

        // Present top 5 timetables
        // Include option to open another 5
    }

    // OnClick: Choose timetable -> ChosenTimetable activity
}
