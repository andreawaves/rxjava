package com.example.rxjava_example;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    //ui
    @BindView(R.id.tv_1)
    TextView tv_1;

    //vars
    private String TAG = "rxjava";
    private CompositeDisposable disposable = new CompositeDisposable(); //helps to keep track of all the observers we are using,
                                                                        //to clean them up when they are not needed

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        tv_1.setText("KEKito");
        //tv_1.setText("HOLA");

        Observable<Task> taskObservable = Observable // create a new Observable object
                .fromIterable(DataSource.createTasksList()) // apply 'fromIterable' operator
                .subscribeOn(Schedulers.io()) // designate worker thread (background)
                .filter(new Predicate<Task>() {

                    @Override
                    public boolean test(Task task) throws Exception { //Filter running in a background thread
                        Log.d(TAG,"test: "+Thread.currentThread().getName()); //background thread
                        try {                           //Frezzing it in a background thread, not frezzing the ui
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return task.isComplete(); //Only shows the tasks that are complete
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()); // designate observer thread (main thread)


        taskObservable.subscribe(new Observer<Task>() {

            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG,"onSubscribe: called.");
            }


            //In the main thread:
            @Override
            public void onNext(Task task) {
                Log.d(TAG,"onNext: "+Thread.currentThread().getName());
                Log.d(TAG,"onNext: "+task.getDescription());
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"onError:"+e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"onComplete: called.");
            }
        });
    }
}
