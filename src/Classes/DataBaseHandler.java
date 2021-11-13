package Classes;
/**
 Interface to read and write data to flat file database
*/
interface DatabaseHandler {
        /**
         * writes data to dat file
         */
        public void serializeToFile();

         /**
         * reads data from dat file
         */
        public void deserializeFromFile();
}
