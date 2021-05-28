<?php

namespace TrainingBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class DefaultController extends Controller
{
    public function trainingAction()
    {
        return $this->render('@Training/list_training.html.twig');
    }
    public function animalsAction()
    {
        return $this->render('@Training/list_animals.html.twig');
    }

    public function fronttrainingAction()
    {
        return $this->render('@Training/front/training.html.twig');
    }
    public function frontanimalsAction()
    {
        return $this->render('@Training/front/animals.html.twig');
    }
}
