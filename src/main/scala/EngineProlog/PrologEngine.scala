package EngineProlog

import Model.Cells.*
import Model.Room.{Room, *}
import alice.tuprolog.{Prolog, SolveInfo, Struct, Term, Theory}

/** a prolog engine for executing [[Prolog]] code
  * @param clauses
  *   the [[Theory]] provided
  */
case class PrologEngine(clauses: Theory*):

  /** the engine, it takes a goal to solve and return a [[LazyList]] of [[SolveInfo]]
    */
  private val prologEngine: Term => LazyList[SolveInfo] =
    val engine = Prolog()
    clauses.foreach(engine.addTheory)
    goal =>
      new Iterable[SolveInfo] {
        override def iterator: Iterator[SolveInfo] = new Iterator[SolveInfo]:
          var solution: Option[SolveInfo] = Some(engine.solve(goal))

          override def hasNext: Boolean = solution match
            case Some(value) => value.isSuccess || value.hasOpenAlternatives
            case None        => false

          override def next(): SolveInfo =
            val sol = solution match
              case Some(value) => value
              case None        => throw Exception()
            solution = if (sol.hasOpenAlternatives) Some(engine.solveNext()) else None
            sol
      }.to(LazyList)

  /** solve the provided goal and return a [[Map]] of its [[Term]]s associated to the values obtained
    * @param goal
    *   the goal to solve
    * @param terms
    *   the terms of the goal
    * @return
    */
  def solve(goal: Term, terms: String*): Map[String, Term] =
    prologEngine(goal).headOption match
      case Some(result) => terms.map(term => (term, result.getTerm(term))).toMap
      case None         => Map.empty

object PrologEngine:

  // class to term
  given Conversion[String, Term] = Term.createTerm(_)
  given Conversion[Seq[_], Term] = _.mkString("[", ",", "]")
  given Conversion[String, Theory] = theoryName =>
    Theory.parseWithStandardOperators(getClass.getResourceAsStream(theoryName))

  // term to class
  given Conversion[Term, List[Int]] = term => "\\d+".r.findAllIn(term.toString).map(_.toInt).toList
  given Conversion[Term, Int] = term => "\\d+".r.findFirstIn(term.toString).get.toInt
