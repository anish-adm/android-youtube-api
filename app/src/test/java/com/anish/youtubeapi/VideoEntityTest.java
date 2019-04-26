package com.anish.youtubeapi;

import com.anish.youtubeapi.data.model.VideoEntity;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.assertEquals;

public class VideoEntityTest {
    private VideoEntity mVideoEntity;
    private final static String TITLE_TEST1 = "Avengers: Endgame";
    private final static String VIDEO_ID_TEST1 = "TcMBFSGVi1c";
    private final static String THUMBNAIL_URL_TEST1 = "https://i.ytimg.com/vi/EnRzM4bE6M4/mqdefault.jpg";
    private final static String TITLE_TEST2 = "Captain Marvel";
    private final static String VIDEO_ID_TEST2 = "Z1BCujX3pw8";
    private final static String THUMBNAIL_URL_TEST2 = "https://i.ytimg.com/vi/vwfVWFOPBCY/mqdefault.jpg";

    @Before
    public void setUp() {
        mVideoEntity = new VideoEntity(TITLE_TEST1, THUMBNAIL_URL_TEST1, VIDEO_ID_TEST1);
    }

    @Test
    public void videoEntityIsNotNull() {
        assertNotNull(mVideoEntity);
    }

    @Test
    public void videoEntityTitleIsCorrect() {
        assertEquals(mVideoEntity.getTitle(), TITLE_TEST1);
        mVideoEntity.setTitle(TITLE_TEST2);
        assertEquals(mVideoEntity.getTitle(), TITLE_TEST2);
    }

    @Test
    public void videoEntityThumbnailIsCorrect() {
        assertEquals(mVideoEntity.getThumbnailURL(), THUMBNAIL_URL_TEST1);
        mVideoEntity.setThumbnailURL(THUMBNAIL_URL_TEST2);
        assertEquals(mVideoEntity.getThumbnailURL(), THUMBNAIL_URL_TEST2);
    }

    @Test
    public void videoEntityIDIsCorrect() {
        assertEquals(mVideoEntity.getVideoId(), VIDEO_ID_TEST1);
        mVideoEntity.setVideoId(VIDEO_ID_TEST2);
        assertEquals(mVideoEntity.getVideoId(), VIDEO_ID_TEST2);
    }

//    @Test
//    public void failTest() {
//        assertEquals(mVideoEntity.getTitle(), TITLE_TEST2);
//    }

}
