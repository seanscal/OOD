/**
 * Created by sean on 1/15/15.
 */
    public class Book implements publication
    {
        private String title, author, publisher, location;
        private int year;

        /**
         *
         * @param title the title of the book
         * @param author the author of the book
         * @param publisher the publisher of the book
         * @param location the location of the book's publisher
         * @param year the year of the book's release
         */

        public Book(String title, String author, String publisher, String location, int year)
        {
            this.title = title;
            this.author = author;
            this.publisher = publisher;
            this.location = location;
            this.year = year;
        }


        @Override
        public String citeAPA(){
            return null;
        }

        @Override
        public String citeMPA(){
            return null;
        }
    }