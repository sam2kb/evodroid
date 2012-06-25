package com.sonorth.evodroid;

import com.sonorth.evodroid.models.Blog;
import com.sonorth.evodroid.models.Comment;
import com.sonorth.evodroid.models.Post;

import android.app.Application;

public class b2evolution extends Application {
    public static Blog currentBlog;
	public static Comment currentComment;
	public static Post currentPost;
	public static b2evolutionDB DB;
	public static OnPostUploadedListener onPostUploadedListener = null;
	public static boolean postsShouldRefresh;
	
	public interface OnPostUploadedListener {
		public abstract void OnPostUploaded();
	}
	
	public static void setOnPostUploadedListener(OnPostUploadedListener listener) {
		onPostUploadedListener = listener;
	}

	public static void postUploaded() {
		if (onPostUploadedListener != null) {
			try {
				onPostUploadedListener.OnPostUploaded();
			} catch (Exception e) {
				postsShouldRefresh = true;
			}
		} else {
			postsShouldRefresh = true;
		}
		
	}
}
