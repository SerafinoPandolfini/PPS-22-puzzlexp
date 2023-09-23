package prologEngine

import model.cells.*
import model.room.*
import alice.tuprolog.{Prolog, SolveInfo, Struct, Term, Theory}

import scala.collection.immutable.Map

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
          var solution: Option[SolveInfo] = Option(engine.solve(goal))

          override def hasNext: Boolean = solution match
            case Some(value) => value.isSuccess || value.hasOpenAlternatives
            case None        => false

          override def next(): SolveInfo =
            val sol = solution match
              case Some(value) => value
              case None        => throw Exception()
            solution = if (sol.hasOpenAlternatives) Option(engine.solveNext()) else None
            sol
      }.to(LazyList)

  /** solve the provided goal and return the possible solutions
    *
    * @param goal
    *   the goal to solve
    * @param terms
    *   the terms of the goal
    * @return
    *   a [[Map]] of the [[Term]]s and the values associated
    */
  def solve(goal: Term, terms: String*): Map[String, Term] =
    prologEngine(goal).headOption match
      case Some(result) => terms.map(term => (term, result.getTerm(term))).toMap
      case None         => Map.empty

  /** solve the provided goal and return if there is a solution or a failure
    *
    * @param goal
    *   the goal to solve
    * @return
    *   true if the goal have a solution, false otherwise
    */
  def solve(goal: Term): Boolean = prologEngine(goal).map(_.isSuccess).headOption match
    case Some(value) => value
    case _           => false

object PrologEngine:

  given Conversion[String, Term] = Term.createTerm(_)
  given Conversion[Seq[_], Term] = _.mkString("[", ",", "]")
  given Conversion[Int, Term] = _.toString
  given Conversion[Set[_], Term] = _.toList
  given Conversion[String, Theory] = theoryName =>
    Theory.parseWithStandardOperators(getClass.getResourceAsStream(theoryName))
  given Conversion[Term, List[Int]] = term => "\\d+".r.findAllIn(term.toString).map(_.toInt).toList
  given Conversion[Term, Int] = term => "\\d+".r.findFirstIn(term.toString).get.toInt
