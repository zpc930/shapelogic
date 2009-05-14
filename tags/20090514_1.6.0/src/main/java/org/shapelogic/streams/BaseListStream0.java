package org.shapelogic.streams;

import java.util.List;


/** Implementation of ListStream. <br />
 * 
 * Close to<br /> 
 * 1: a lazy stream.<br />
 * 2: a UNIX pipe.<br />
 * <br />
 * This is close to org.apache.commons.collections.list.LazyList,
 * but that takes a factory to calculate next element, and that works badly here.
 * 
 * How should a step work?<br />
 * Normally call the hasNext, in order for this to work it would have to do the 
 * calculation.<br />
 * <br />
 * So _current should be the last element that has been returned.<br />
 * <br />
 * So when you call hasNext it will add that element to the list.
 * When you call next it will bump the counter up one element if goes too high 
 * then do the calculation.
 * 
 * At the end it will get to a point where the calculation fails. 
 * At that point last will be set to the current element.
 * 
 * I will ignore synchronization to begin with.
 * 
 * @author Sami Badawi
 *
 */
abstract public class BaseListStream0<E> extends BaseListCommonStream<E> implements IndexedInputStream0<E> {
	
	public BaseListStream0(int maxLast){
		setMaxLast(maxLast);
	}
	
	public BaseListStream0(){
	}

	@Override
	public E invokeIndex(int index){
		return invoke(index);
	}
	
	public List<E> getList() { 
		return _list;
	}

	@Override
	public void setList(List<E> list) {
		_list = list;
	}
}
