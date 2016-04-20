package me.zjc.swfu;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import me.zjc.swfu.netWork.client.PublicNotifyClient;
import me.zjc.swfu.netWork.response.NotifyDetail;
import me.zjc.swfu.util.MD5Util;
import rx.Subscriber;
import rx.schedulers.Schedulers;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        System.out.println(MD5Util.getMD5Str("admin"));
    }

}