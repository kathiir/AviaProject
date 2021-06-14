package com.example.aviaapplication.utils;

import androidx.lifecycle.LifecycleOwner;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LiveMessageEvent<T> extends SingleLiveEvent<Consumer<T>> {

    public final void setEventReceiver(@NotNull LifecycleOwner owner, T receiver) {
        this.observe(owner, event -> {
            if (event != null)
                event.invoke(receiver);
        });

    }

    public final void sendEvent(@Nullable Consumer<T> event) {
        this.setValue(event);
    }
}

//public class LiveMessageEvent<T> extends SingleLiveEvent<T> {
//
//    public void setEventReceiver(LifecycleOwner owner, T receiver){
//        observe(owner, (Observer) o -> {
//            if (o != null)
//                receiver.
//        });
//    }
//
//    public void setEventReceiver(owner:LifecycleOwner, receiver: T) {
//        observe(owner, Observer { event ->
//        if ( event != null ) {
//        receiver.event()
//        }
//        })
//        }
//
//        public void sendEvent(event: (T.() -> Unit)?) {
//            value = event
//        }
//        }