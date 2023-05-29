package com.example.spycamsecurity.common

import kotlinx.coroutines.Job

/* we want to share behavior across child classes;
    these are protected methods;

    EVENT: generic
 */
abstract class BaseLogic<EVENT> {
    /* cancel child coroutines and make logic classes as
        their own coroutine scopes with jobTracker: Job
     */
    protected lateinit var jobTracker: Job
    abstract fun onEvent(event: EVENT)
}