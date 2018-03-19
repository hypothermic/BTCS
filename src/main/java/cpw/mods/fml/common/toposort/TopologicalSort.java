package cpw.mods.fml.common.toposort;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class TopologicalSort
{
  public static class DirectedGraph<T>
    implements Iterable<T>
  {
    private final Map<T, SortedSet<T>> graph = new HashMap();
    private List<T> orderedNodes = new ArrayList();
    

    public boolean addNode(T node)
    {
      if (this.graph.containsKey(node))
      {
        return false;
      }
      
      this.orderedNodes.add(node);
      this.graph.put(node, new TreeSet<T>(new Comparator<T>()
      {
          public int compare(T o1, T o2) {
              return orderedNodes.indexOf(o1)-orderedNodes.indexOf(o2);
          }
      }));
      return true;
    }
    
    public void addEdge(T from, T to)
    {
      if ((!this.graph.containsKey(from)) || (!this.graph.containsKey(to)))
      {
        throw new NoSuchElementException("Missing nodes from graph");
      }
      
      ((SortedSet)this.graph.get(from)).add(to);
    }
    
    public void removeEdge(T from, T to)
    {
      if ((!this.graph.containsKey(from)) || (!this.graph.containsKey(to)))
      {
        throw new NoSuchElementException("Missing nodes from graph");
      }
      
      ((SortedSet)this.graph.get(from)).remove(to);
    }
    
    public boolean edgeExists(T from, T to)
    {
      if ((!this.graph.containsKey(from)) || (!this.graph.containsKey(to)))
      {
        throw new NoSuchElementException("Missing nodes from graph");
      }
      
      return ((SortedSet)this.graph.get(from)).contains(to);
    }
    
    public Set<T> edgesFrom(T from)
    {
      if (!this.graph.containsKey(from))
      {
        throw new NoSuchElementException("Missing node from graph");
      }
      
      return Collections.unmodifiableSortedSet((SortedSet)this.graph.get(from));
    }
    
    public Iterator<T> iterator()
    {
      return this.orderedNodes.iterator();
    }
    
    public int size()
    {
      return this.graph.size();
    }
    
    public boolean isEmpty()
    {
      return this.graph.isEmpty();
    }
    

    public String toString()
    {
      return this.graph.toString();
    }
  }
  







  public static <T> List<T> topologicalSort(DirectedGraph<T> graph)
  {
    DirectedGraph<T> rGraph = reverse(graph);
    List<T> sortedResult = new ArrayList();
    Set<T> visitedNodes = new HashSet();
    
    Set<T> expandedNodes = new HashSet();
    
    for (T node : rGraph)
    {
      explore(node, rGraph, sortedResult, visitedNodes, expandedNodes);
    }
    
    return sortedResult;
  }
  
  public static <T> DirectedGraph<T> reverse(DirectedGraph<T> graph)
  {
    DirectedGraph<T> result = new DirectedGraph();
    
    for (T node : graph)
    {
      result.addNode(node);
    }
    
    // BTCS start
    /* for (Iterator i$ = graph.iterator(); i$.hasNext();) { from = i$.next();
      
      for (T to : graph.edgesFrom(from))
      {
        result.addEdge(to, from);
      }
    }*/
    for (T from : graph)
    {
        for (T to : graph.edgesFrom(from))
        {
            result.addEdge(to, from);
        }
    }
    // BTCS end
    T from;
    return result;
  }
  

  public static <T> void explore(T node, DirectedGraph<T> graph, List<T> sortedResult, Set<T> visitedNodes, Set<T> expandedNodes)
  {
    if (visitedNodes.contains(node))
    {

      if (expandedNodes.contains(node))
      {

        return;
      }
      
      System.out.printf("%s: %s\n%s\n%s\n", new Object[] { node, sortedResult, visitedNodes, expandedNodes });
      throw new IllegalArgumentException("There was a cycle detected in the input graph, sorting is not possible");
    }
    

    visitedNodes.add(node);
    

    for (T inbound : graph.edgesFrom(node))
    {
      explore(inbound, graph, sortedResult, visitedNodes, expandedNodes);
    }
    

    sortedResult.add(node);
    
    expandedNodes.add(node);
  }
}
