# ArchDoc

Jupyter Demo: [Sample](sample.ipynb)

## Tasks

Kernel with [https://github.com/Kotlin/kotlin-jupyter](https://github.com/Kotlin/kotlin-jupyter)

UI with [https://github.com/jupyterlab/jupyterlab](https://github.com/jupyterlab/jupyterlab)

- [x] REPL tests
- [x] basic DSL design
- [ ] Frontend Samples
   - [ ] [https://github.com/datalayer/jupyter-react](https://github.com/datalayer/jupyter-react)
- [ ] Archdoc Editor
  - [ ] CodeMirror or Monaco Editor
  - [ ] Parser: `marked`
  - [ ] Math: `mathjax2`
- [ ] Archdoc Server
  - [ ] Java kernel with [https://github.com/twosigma/beakerx](https://github.com/twosigma/beakerx)
  - [ ] Jupyter Kernel Server with Kotlin
     - [ ] Kotlin Jupyter Protocol Server (Socket)
  - [ ] Jupyter Protocol for frontend
  - [ ] magic (%) support (ext_plugins) (%%archdoc)
- [ ] Markdown Parser
  - [ ] [Kotlin markdown](https://github.com/JetBrains/markdown)
  - [ ] [flexmark-java](https://github.com/vsch/flexmark-java)
- [ ] Online GraphEngine
  - [ ] ArchGuard Graph
  - [ ] with D3.js ?
  - [ ] with Echart.js ?

DSL:

- [ ] Backend CRUD DSL
- [ ] Linter DSL
- [ ] Scanner DSL
- [ ] Architecture DSL
- [ ] Governance DSL

Graph Features:

- [ ] UML
   - [ ] by extensions? like [yUML](https://yuml.me/), websequencediagrams ?
   - [ ] UML with mermaid
-[ ] custom Graph Engine

Frontend:

- [ ] Componentless Architecture with plugins
   - [ ] every plugin as WebComponents
- [ ] plugin Component API design

Spike:

- [ ] Apache Calcite (for SQL parser)
  - JAVAC for AST 
  - Janino, java compiler
- [ ] Apache Beam (for pipeline model)
- [ ] Apache Spark (for pipeline design)

Apache Spark sample

```java
Dataset df = spark.read().json("logs.json");
df.where("age > 21")
  .select("name.first").show();
```

## DSL

```kotlin
@file:DependsOn("org.archguard.scanner:doc-executor:2.0.0-alpha.2")
import org.archguard.dsl.*
var layer = layered {
    prefixId("org.archguard")
    component("controller") dependentOn component("service")
    组件("service") 依赖于 组件("repository")
}
```

## Two types Query

### Struct Query

[Guarding](https://github.com/modernizing/guarding) like:

```
class(implementation "BaseParser")::name should endsWith "Parser";

class("java.util.Map") only accessed(["com.phodal.pepper.refactor.staticclass"]);
class(implementation "BaseParser")::name should not contains "Lexer";
```

### SQL-like Query? LINQ? 

[](https://spark.apache.org/docs/latest/structured-streaming-programming-guide.html) like:

```
// ========== DF with aggregation ==========
Dataset<Row> aggDF = df.groupBy("device").count();

// Print updated aggregations to console
aggDF
  .writeStream()
  .outputMode("complete")
  .format("console")
  .start();

// Have all the aggregates in an in-memory table
aggDF
  .writeStream()
  .queryName("aggregates")    // this query name will be the table name
  .outputMode("complete")
  .format("memory")
  .start();

spark.sql("select * from aggregates").show();   // interactively query in-memory table
```


libs:

- [https://github.com/kotlin-orm/ktorm](https://github.com/kotlin-orm/ktorm)
- [JetBrains Exposed](https://github.com/JetBrains/Exposed)

KtORM sample:

```kotlin
val t = Employees.aliased("t")
database
    .from(t)
    .select(t.departmentId, avg(t.salary))
    .groupBy(t.departmentId)
    .having { avg(t.salary) greater 100.0 }
    .forEach { row -> 
        println("${row.getInt(1)}:${row.getDouble(2)}")
    }
```

## Setup

1. setup jupyter with Kotlin: [https://github.com/Kotlin/kotlin-jupyter](https://github.com/Kotlin/kotlin-jupyter) 
   1. with Conda
      - download from [https://repo.anaconda.com/](https://repo.anaconda.com/) 
      - `conda install -c jetbrains kotlin-jupyter-kernel`
   2. with pip: `pip install kotlin-jupyter-kernel` 
2. save with `.ipynb` for local file
3. try editor api ?
4. analysis editor api?
5. design poc editor

## Resources

[https://github.com/nteract/nteract](https://github.com/nteract/nteract) is an open-source organization committed to creating fantastic interactive computing experiences that allow people to collaborate with ease.

[Jupyter Notebook](https://docs.jupyter.org/en/latest/projects/architecture/content-architecture.html)

![Basic Architecture](https://docs.jupyter.org/en/latest/_images/notebook_components.png)

![Overview](https://docs.jupyter.org/en/latest/_images/repos_map.png)



