import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IHorarioCursada, defaultValue } from 'app/shared/model/horario-cursada.model';

export const ACTION_TYPES = {
  FETCH_HORARIOCURSADA_LIST: 'horarioCursada/FETCH_HORARIOCURSADA_LIST',
  FETCH_HORARIOCURSADA: 'horarioCursada/FETCH_HORARIOCURSADA',
  CREATE_HORARIOCURSADA: 'horarioCursada/CREATE_HORARIOCURSADA',
  UPDATE_HORARIOCURSADA: 'horarioCursada/UPDATE_HORARIOCURSADA',
  DELETE_HORARIOCURSADA: 'horarioCursada/DELETE_HORARIOCURSADA',
  RESET: 'horarioCursada/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IHorarioCursada>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type HorarioCursadaState = Readonly<typeof initialState>;

// Reducer

export default (state: HorarioCursadaState = initialState, action): HorarioCursadaState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_HORARIOCURSADA_LIST):
    case REQUEST(ACTION_TYPES.FETCH_HORARIOCURSADA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_HORARIOCURSADA):
    case REQUEST(ACTION_TYPES.UPDATE_HORARIOCURSADA):
    case REQUEST(ACTION_TYPES.DELETE_HORARIOCURSADA):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_HORARIOCURSADA_LIST):
    case FAILURE(ACTION_TYPES.FETCH_HORARIOCURSADA):
    case FAILURE(ACTION_TYPES.CREATE_HORARIOCURSADA):
    case FAILURE(ACTION_TYPES.UPDATE_HORARIOCURSADA):
    case FAILURE(ACTION_TYPES.DELETE_HORARIOCURSADA):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_HORARIOCURSADA_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_HORARIOCURSADA):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_HORARIOCURSADA):
    case SUCCESS(ACTION_TYPES.UPDATE_HORARIOCURSADA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_HORARIOCURSADA):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/horario-cursadas';

// Actions

export const getEntities: ICrudGetAllAction<IHorarioCursada> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_HORARIOCURSADA_LIST,
  payload: axios.get<IHorarioCursada>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IHorarioCursada> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_HORARIOCURSADA,
    payload: axios.get<IHorarioCursada>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IHorarioCursada> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_HORARIOCURSADA,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IHorarioCursada> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_HORARIOCURSADA,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IHorarioCursada> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_HORARIOCURSADA,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
