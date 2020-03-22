/**
 * 
 */
package ningyuan.pan.servicex.util;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

/**
 * @author ningyuan
 *
 */
public class ServiceXUtil {

	 private ServiceXUtil() {} 
	 
	 private static class ServiceXUtilInstance { 
		 private static final ServiceXUtil INSTANCE = new ServiceXUtil(); 
	 } 
	 
	 private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
	 
	 private final ReadLock readLock = lock.readLock();
	 private final WriteLock writeLock = lock.writeLock();
	 
	 private final Map<String, Object> context = new HashMap<String, Object>();
	 
	 public static ServiceXUtil getInstance() { 
		 return ServiceXUtilInstance.INSTANCE; 
	 } 

	 public Object getGelobalObject(String name) {
		 readLock.lock();
		 
		 try {
			 return context.get(name);
		 }
		 finally {
			readLock.unlock();
		}
	 }
	 
	 public void setGelobalObject(String name, Object value) {
		 writeLock.lock();
		 
		 try {
			context.put(name, value);
		 }
		 finally {
			writeLock.unlock();
		}
	 }
	 
	 public boolean setIfAbsent(String name, Object value) {
		 writeLock.lock();
		 
		 try {
			 if(context.containsKey(name)) {
				 return false;
			 }
			 else {
				 context.put(name, value);
				 return true;
			 }
		 }
		 finally {
			writeLock.unlock();
		}
	 }
	 
	 public void removeGelobalObject(String name) {
		 writeLock.lock();
		 
		 try {
			 context.remove(name);
		 }
		 finally {
			writeLock.unlock();
		}
	 }
}
