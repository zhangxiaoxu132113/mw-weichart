package com.water.wechat.framework.message.resp;

/**
 * ClassName: MusicMessage
 * @Description: 音乐消息
 */
public class MusicMessage extends BaseMessage {  
    // 音乐   
    private Music Music;  
  
    public Music getMusic() {  
        return Music;  
    }  
  
    public void setMusic(Music music) {  
        Music = music;  
    }  
}  
