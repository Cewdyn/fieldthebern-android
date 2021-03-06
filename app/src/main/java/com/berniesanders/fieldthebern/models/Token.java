/*
 * Copyright (c) 2016 - Bernie 2016, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.berniesanders.fieldthebern.models;

import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.SerializedName;

/**
 * See <a href="https://github.com/Bernie-2016/ground-game-api/wiki/API-Tokens">https://github.com/Bernie-2016/ground-game-api/wiki/API-Tokens</a>
 *
 * {
 * "access_token":"66ba7ffd6bee6a62be4ae1c443217de4c32d7124b6eb3b7bb364db0dc3af003a",
 * "token_type":"bearer",
 * "expires_in":7200,
 * "created_at":1447745123,
 * "refresh_token":"da98ead980980ade098ead098ade098aed08ade08"
 * "user_id":29
 * }
 */
public final class Token implements Parcelable {

  public static final String PREF_NAME = Token.class.getName();
  public static final String GRANT_REFRESH = "refresh_token";

  /**
   * zeee token
   */
  @SerializedName("access_token")
  private String accessToken;

  /**
   * zeee most freshiest token
   */
  @SerializedName("refresh_token")
  private String refreshToken;

  /**
   * should be 'bearer' or ?
   */
  @SerializedName("token_type")
  private String type;

  /**
   * User id
   */
  @SerializedName("user_id")
  private int userId;

  /**
   * time_in_seconds, usually about 2hrs
   */
  @SerializedName("expires_in")
  private int expires;

  /**
   * time_of_creation_as_integer
   * seconds since epoch...?
   */
  @SerializedName("created_at")
  private long created;

  public boolean isExpired(long currentTimeMs) {
    return (currentTimeMs >= ((created + expires) * 1000));
  }

  public String accessToken() {
    return accessToken;
  }

  public Token accessToken(String accessToken) {
    this.accessToken = accessToken;
    return this;
  }

  public String type() {
    return type;
  }

  public Token type(String type) {
    this.type = type;
    return this;
  }

  public int userId() {
    return userId;
  }

  public Token userId(int userId) {
    this.userId = userId;
    return this;
  }

  public int expires() {
    return expires;
  }

  public Token expires(int expires) {
    this.expires = expires;
    return this;
  }

  public long created() {
    return created;
  }

  public Token created(long created) {
    this.created = created;
    return this;
  }

  public String refreshToken() {
    return this.refreshToken;
  }

  public Token refreshToken(final String refreshToken) {
    this.refreshToken = refreshToken;
    return this;
  }

  @Override
  public int describeContents() {
    return 0;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.accessToken);
    dest.writeString(this.refreshToken);
    dest.writeString(this.type);
    dest.writeInt(this.userId);
    dest.writeInt(this.expires);
    dest.writeLong(this.created);
  }

  public Token() {
  }

  protected Token(Parcel in) {
    this.accessToken = in.readString();
    this.refreshToken = in.readString();
    this.type = in.readString();
    this.userId = in.readInt();
    this.expires = in.readInt();
    this.created = in.readLong();
  }

  public static final Parcelable.Creator<Token> CREATOR = new Parcelable.Creator<Token>() {
    public Token createFromParcel(Parcel source) {
      return new Token(source);
    }

    public Token[] newArray(int size) {
      return new Token[size];
    }
  };
}
