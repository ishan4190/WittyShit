package com.example.varun.thewittyshit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
/**
 * Created by Varun on 4/12/2016.
 */
public class Witt implements Serializable{


    @JsonProperty("tags")
    private String tags="";

    @JsonProperty("contentImage")
    private String contentImage="";

    @JsonProperty("owner_name")
    private String owner_name="";

    @JsonProperty("youTubeURL")
        private String youTubeURL="";
    @JsonProperty("Type")
        private String Type="";
    @JsonProperty("rofl")
        private Integer rofl=0;
    @JsonProperty("lol")
        private Integer lol=0;
    @JsonProperty("lmao")
        private Integer lmao=0;
    @JsonProperty("shit")
        private Integer shit=0;
    @JsonProperty("date_created")
        private long date_created;
    @JsonProperty("owner_image")
        private String owner_image="";
    @JsonProperty("category")
        private String category="";
    @JsonProperty("description")
        private String description="";
    @JsonProperty("lattitude")
        private String lattitude="";
    @JsonProperty("longitude")
        private String longitude="";
    @JsonProperty("place")
        private String place="";
    @JsonProperty("tags")
        public String getTags ()
        {
            return tags;
        }
    @JsonProperty("tags")
        public void setTags (String tags)
        {
            this.tags = tags;
        }
    @JsonProperty("contentImage")
        public String getContentImage ()
        {
            return contentImage;
        }
    @JsonProperty("contentImage")
        public void setContentImage (String contentImage)
        {
            this.contentImage = contentImage;
        }
    @JsonProperty("owner_name")
        public String getOwner_name ()
        {
            return owner_name;
        }
    @JsonProperty("owner_name")
        public void setOwner_name (String owner_name)
        {
            this.owner_name = owner_name;
        }
    @JsonProperty("youTubeURL")
        public String getYouTubeURL ()
        {
            return youTubeURL;
        }
    @JsonProperty("youTubeURL")
        public void setYouTubeURL (String youTubeURL)
        {
            this.youTubeURL = youTubeURL;
        }
    @JsonProperty("Type")
        public String getType ()
        {
            return Type;
        }
    @JsonProperty("Type")
        public void setType (String Type)
        {
            this.Type = Type;
        }
    @JsonProperty("rofl")
        public Integer getRofl ()
        {
            return rofl;
        }
    @JsonProperty("rofl")
        public void setRofl (int rofl)
        {
            this.rofl = rofl;
        }
    @JsonProperty("date_created")
        public long getDate_created ()
        {
            return date_created;
        }
    @JsonProperty("date_created")
        public void setDate_created (long date_created)
        {
            this.date_created = date_created;
        }
    @JsonProperty("owner_image")
        public String getOwner_image ()
        {
            return owner_image;
        }
    @JsonProperty("owner_image")
        public void setOwner_image (String owner_image)
        {
            this.owner_image = owner_image;
        }
    @JsonProperty("category")
        public String getCategory ()
        {
            return category;
        }
    @JsonProperty("category")
        public void setCategory (String category)
        {
            this.category = category;
        }
    @JsonProperty("description")
        public String getDescription ()
        {
            return description;
        }
    @JsonProperty("description")
        public void setDescription (String description)
        {
            this.description = description;
        }
    @JsonProperty("lol")
    public Integer getlol ()
    {
        return lol;
    }

    @JsonProperty("lol")
    public void setlol (Integer lol)
    {
        this.lol = lol;
    }
    @JsonProperty("lattitude")
        public String getLattitude ()
        {
            return lattitude;
        }
    @JsonProperty("lattitude")
        public void setLattitude (String lattitude)
        {
            this.lattitude = lattitude;
        }
    @JsonProperty("longitude")
        public String getLongitude ()
        {
            return longitude;
        }
    @JsonProperty("longitude")
        public void setLongitude (String longitude)
        {
            this.longitude = longitude;
        }
    @JsonProperty("place")
        public String getPlace ()
        {
            return place;
        }
    @JsonProperty("place")
        public void setPlace (String place)
        {
            this.place = place;
        }
    @JsonProperty("lmao")
        public Integer getLmao ()
        {
            return lmao;
        }
    @JsonProperty("lmao")
        public void setLmao (Integer lmao)
        {
            this.lmao = lmao;
        }
    @JsonProperty("shit")
         public Integer getShit ()
         {
        return shit;
         }
    @JsonProperty("shit")
         public void setshit (Integer shit)
         {
          this.shit = shit;
         }

        @Override
        public String toString()
        {
            return "Witt [tags = "+tags+", contentImage = "+contentImage+", owner_name = "+owner_name+", youTubeURL = "+youTubeURL+", Type = "+Type+", rofl = "+rofl.toString()+", date_created = "+date_created+", owner_image = "+owner_image+", category = "+category+", description = "+description+", lol = "+lol.toString()+", lattitude = "+lattitude+", longitude = "+longitude+", place = "+place+", lmao = "+lmao.toString()+", shit = "+shit.toString()+"]";
        }
    }



