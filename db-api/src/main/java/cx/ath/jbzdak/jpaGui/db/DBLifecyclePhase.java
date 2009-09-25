package cx.ath.jbzdak.jpaGui.db;

/**
 * @author Jacek Bzdak jbzdak@gmail.com
 *         Date: 2009-09-11
 */
public enum DBLifecyclePhase {
   /**
    * Events that are to be fired before database even start.
    * This phase is mandatory. Can be used to configure database.
    */
   PRE_START,
   START, 
   /**
    *  Events to be fired after database is started, but before schema is created.
    *  This phase is fired only when database doesnt have a schema, i.e. it was not created.
    */
   SHEMA_CREATE,
   /**
    *  This phase is fired when we need to update db schemata.
    */
   SHEMA_UPDATE,
   /**
    *  Fired after database is set up. Everything is in place now, and program
    *  may resume normal work.
    *  This phase is mandatory.
    */
   DB_SETUP,
   /**
    * Fired before data is backed up.
    * This phase is fired only when backup event is scheduled.
    */
   PRE_BACKUP,
   /**
    * Backup phase
    */
   BACKUP,
   /**
    * Fired after db is resumed.
    */
   POST_BACKUP,
   PRE_READ_BACKUP,
   READ_BACKUP,
   POST_READ_BACKUP,
   CLEAR_DB_CONTENTS,
   /**
    * Fired before database is stopped.
    */
   CLOSE
}
