<?php

namespace EventsBundle\Controller;

use Symfony\Bundle\FrameworkBundle\Controller\Controller;

class DefaultController extends Controller
{
    public function competitionAction()
    {
        return $this->render('@Events/dashboard/competition.html.twig');
    }
    public function publicityAction()
    {
        return $this->render('@Events/dashboard/publicity.html.twig');
    }
    public function fronteventAction()
    {
        return $this->render('@Events/front/event.html.twig');
    }
    public function fronteventsingleAction()
    {
        return $this->render('@Events/front/event-single.html.twig');
    }

}
