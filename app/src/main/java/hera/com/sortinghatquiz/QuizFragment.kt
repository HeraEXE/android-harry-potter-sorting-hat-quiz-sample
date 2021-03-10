package hera.com.sortinghatquiz

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import com.google.android.material.snackbar.Snackbar
import hera.com.sortinghatquiz.databinding.FragmentQuizBinding

class QuizFragment : Fragment() {

    data class Question(val question: String, val answers: List<String>)

    private lateinit var binding: FragmentQuizBinding
    // 0 - Gryffindor
    // 1 - Ravenclaw
    // 2 - Hufflepuff
    // 3 - Slytherin
    private val pointsOfHouses = arrayOf(0, 0, 0, 0)
    private val questions = listOf(
            Question(
                    question = "You are making a PowerPoint presentation for a class project. You...",
                    answers = listOf(
                            "Do as little as you can. Ah, the perks of group projects.",
                            "Do most of the research and writing, but let other people make it flashy",
                            "Do a little of everything.",
                            "Take charge, organize everyone, and end up doing almost everything"
                    )
            ),
            Question(
                    question = "Do you cheat in class?",
                    answers = listOf(
                            "You try not to, but sometimes it is the only way to start",
                            "If you count sharing answers and helping friends who are struggling.",
                            "Absolutely not!",
                            "Yes, a fair amount. Everybody does it..."
                    )
            ),
            Question(
                    question = "When you die, you hope to...",
                    answers = listOf(
                            "Have achieved success and traveled the world.",
                            "Have learned everything there is to know.",
                            "Be surrounded by lifelong friends.",
                            "Be wealthy enough to provide for many generations of your family."
                    )
            ),
            Question(
                    question = "You see a group of bullies picking on a nerd. You...",
                    answers = listOf(
                            "Go up to the bullies and yell in their faces.",
                            "Sympathize with the nerd but walk on by.",
                            "Find the nerd afterwards and reach out to him.",
                            "Think it is a little funny."
                    )
            ),
            Question(
                    question = "Which of these entertainment professions most appeals to you?",
                    answers = listOf(
                            "Screenwriter.",
                            "Entertainment lawyer.",
                            "Agent.",
                            "Major movie star."
                    )
            ),
            Question(
                    question = "How do you flirt with someone you like? ",
                    answers = listOf(
                            "You do not; you will tell someone straight-up if you like them.",
                            "Try to have a real, deep conversation",
                            "You make witty jokes and smile coyly to charm the pants off them.",
                            "You deliver a great, cheesy pick-up or wink at them."
                    )
            ),
            Question(
                    question = "In high school, your favorite extracurricular was...",
                    answers = listOf(
                            "A student club.",
                            "A debate or academic team.",
                            "A sports team.",
                            "Student government."
                    )
            ),
            Question(
                    question = "You get home from the corner store and notice you were a little undercharged. You...",
                    answers = listOf(
                            "Will tell them next time you shop there... if you ever go back.",
                            "Do not worry about it; it is only a few dollars off.",
                            "Drive back to the store and pay the difference.",
                            "Can not believe your luck."
                    )
            ),
            Question(
                    question = "Your idea of a worthwhile summer vacation is...",
                    answers = listOf(
                            "Parachuting or diving off the cliffs of Acapulco",
                            "Curling up with a good book on the porch.",
                            "Summer school.",
                            "Hanging out and catching up with friends back home."
                    )
            ),
            Question(
                    question = "You hear that a girl in your class just switched to writing the same essay topic you've been working on since September! You...",
                    answers = listOf(
                            "Confront her and ask her not to copy your idea.",
                            "Don't care; your good work will speak for itself.",
                            "Resent her from afar but say nothing.",
                            "Make an appointment with the professor and slip in how you came up with the idea first."
                    )
            ),
            Question(
                    question = "How do you get your time alone when you want it?",
                    answers = listOf(
                            "Do errands or go for a drive.",
                            "Tell your friends it is reflection time and retreat to your room.",
                            "Cancel on a movie date with friends",
                            "Disappear for long periods without a word."
                    )
            ),
            Question(
                    question = "When planning a group trip, you are the one who...",
                    answers = listOf(
                            "Follow the leader and cast your vote when needed. You are on the trip to relax, not stress.",
                            "Recommend some planning options. You're the backup leader.",
                            "Let others worry about destinations and logistics. You are along for a wild ride.",
                            "Worries most about travel plans and lodging. You probably take the responsibility of planning if you can."
                    )
            )
    )
    private var qInd = -1


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false)

        setQuestionOrGetResult()
        binding.submitButton.setOnClickListener {
            getAnswer(it)
        }

        return binding.root
    }

    private fun setQuestionOrGetResult() {
        // Set Question
        qInd++
        if (qInd < questions.size) {
            (activity as AppCompatActivity).supportActionBar?.title = "Question $qInd/12"
            val shuffledQuestions = questions[qInd].answers.shuffled()
            binding.questionTitle.text = questions[qInd].question
            binding.radio1.text = shuffledQuestions[0]
            binding.radio2.text = shuffledQuestions[1]
            binding.radio3.text = shuffledQuestions[2]
            binding.radio4.text = shuffledQuestions[3]
        }
        // Get Result
        else {
            val winInd = pointsOfHouses.indexOf(findMax())
            when (winInd) {
                0 -> Navigation.findNavController(binding.root).navigate(R.id.action_quizFragment_to_gryffindorFragment)
                1 -> Navigation.findNavController(binding.root).navigate(R.id.action_quizFragment_to_ravenclawFragment)
                2 -> Navigation.findNavController(binding.root).navigate(R.id.action_quizFragment_to_hufflepuffFragment)
                3 -> Navigation.findNavController(binding.root).navigate(R.id.action_quizFragment_to_slytherinFragment)
                else -> Navigation.findNavController(binding.root).navigate(R.id.action_quizFragment_to_gryffindorFragment)
            }
        }
    }

    private fun getAnswer(it: View) {
        val radioID = binding.radioGroup.checkedRadioButtonId
        val answer: CharSequence
        if (radioID != -1) {
            answer = when (radioID) {
                binding.radio1.id -> binding.radio1.text
                binding.radio2.id -> binding.radio2.text
                binding.radio3.id -> binding.radio3.text
                else -> binding.radio4.text
            }
            when (answer.toString()) {
                questions[qInd].answers[0] -> pointsOfHouses[0]++
                questions[qInd].answers[1] -> pointsOfHouses[1]++
                questions[qInd].answers[2] -> pointsOfHouses[2]++
                questions[qInd].answers[3] -> pointsOfHouses[3]++
            }
            setQuestionOrGetResult()
        }
        else Snackbar.make(it, "Choose one of the answers!", Snackbar.LENGTH_SHORT).show()
    }

    private fun findMax(): Int {
        var max = 0
        for (i in pointsOfHouses) if (max < i) max = i
        return max
    }
}