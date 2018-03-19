package cpw.mods.fml.common.toposort;

import cpw.mods.fml.common.FMLModContainer;
import cpw.mods.fml.common.ModContainer;
import java.util.Arrays;
import java.util.List;
import java.util.Map;




















public class ModSorter
{
  private TopologicalSort.DirectedGraph<ModContainer> modGraph;
  private ModContainer beforeAll = new FMLModContainer("DummyBeforeAll");
  private ModContainer afterAll = new FMLModContainer("DummyAfterAll");
  private ModContainer before = new FMLModContainer("DummyBefore");
  private ModContainer after = new FMLModContainer("DummyAfter");
  
  public ModSorter(List<ModContainer> modList, Map<String, ModContainer> nameLookup)
  {
    buildGraph(modList, nameLookup);
  }
  
  private void buildGraph(List<ModContainer> modList, Map<String, ModContainer> nameLookup)
  {
    this.modGraph = new TopologicalSort.DirectedGraph();
    this.modGraph.addNode(this.beforeAll);
    this.modGraph.addNode(this.before);
    this.modGraph.addNode(this.afterAll);
    this.modGraph.addNode(this.after);
    this.modGraph.addEdge(this.before, this.after);
    this.modGraph.addEdge(this.beforeAll, this.before);
    this.modGraph.addEdge(this.after, this.afterAll);
    
    for (ModContainer mod : modList)
    {
      this.modGraph.addNode(mod);
    }
    
    for (ModContainer mod : modList)
    {
      boolean preDepAdded = false;
      boolean postDepAdded = false;
      
      for (String dep : mod.getPreDepends())
      {
        preDepAdded = true;
        
        if (dep.equals("*"))
        {

          this.modGraph.addEdge(mod, this.afterAll);
          this.modGraph.addEdge(this.after, mod);
          postDepAdded = true;
        }
        else
        {
          this.modGraph.addEdge(this.before, mod);
          if (nameLookup.containsKey(dep)) {
            this.modGraph.addEdge(nameLookup.get(dep), mod);
          }
        }
      }
      
      for (String dep : mod.getPostDepends())
      {
        postDepAdded = true;
        
        if (dep.equals("*"))
        {

          this.modGraph.addEdge(this.beforeAll, mod);
          this.modGraph.addEdge(mod, this.before);
          preDepAdded = true;
        }
        else
        {
          this.modGraph.addEdge(mod, this.after);
          if (nameLookup.containsKey(dep)) {
            this.modGraph.addEdge(mod, nameLookup.get(dep));
          }
        }
      }
      
      if (!preDepAdded)
      {
        this.modGraph.addEdge(this.before, mod);
      }
      
      if (!postDepAdded)
      {
        this.modGraph.addEdge(mod, this.after);
      }
    }
  }
  
  public List<ModContainer> sort()
  {
    List<ModContainer> sortedList = TopologicalSort.topologicalSort(this.modGraph);
    sortedList.removeAll(Arrays.asList(new ModContainer[] { this.beforeAll, this.before, this.after, this.afterAll }));
    return sortedList;
  }
}
