import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { IPrioridad, defaultValue } from 'app/shared/model/prioridad.model';

export const ACTION_TYPES = {
  FETCH_PRIORIDAD_LIST: 'prioridad/FETCH_PRIORIDAD_LIST',
  FETCH_PRIORIDAD: 'prioridad/FETCH_PRIORIDAD',
  CREATE_PRIORIDAD: 'prioridad/CREATE_PRIORIDAD',
  UPDATE_PRIORIDAD: 'prioridad/UPDATE_PRIORIDAD',
  DELETE_PRIORIDAD: 'prioridad/DELETE_PRIORIDAD',
  RESET: 'prioridad/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<IPrioridad>,
  entity: defaultValue,
  updating: false,
  updateSuccess: false
};

export type PrioridadState = Readonly<typeof initialState>;

// Reducer

export default (state: PrioridadState = initialState, action): PrioridadState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_PRIORIDAD_LIST):
    case REQUEST(ACTION_TYPES.FETCH_PRIORIDAD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_PRIORIDAD):
    case REQUEST(ACTION_TYPES.UPDATE_PRIORIDAD):
    case REQUEST(ACTION_TYPES.DELETE_PRIORIDAD):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_PRIORIDAD_LIST):
    case FAILURE(ACTION_TYPES.FETCH_PRIORIDAD):
    case FAILURE(ACTION_TYPES.CREATE_PRIORIDAD):
    case FAILURE(ACTION_TYPES.UPDATE_PRIORIDAD):
    case FAILURE(ACTION_TYPES.DELETE_PRIORIDAD):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRIORIDAD_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.FETCH_PRIORIDAD):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_PRIORIDAD):
    case SUCCESS(ACTION_TYPES.UPDATE_PRIORIDAD):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_PRIORIDAD):
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

const apiUrl = 'api/prioridads';

// Actions

export const getEntities: ICrudGetAllAction<IPrioridad> = (page, size, sort) => ({
  type: ACTION_TYPES.FETCH_PRIORIDAD_LIST,
  payload: axios.get<IPrioridad>(`${apiUrl}?cacheBuster=${new Date().getTime()}`)
});

export const getEntity: ICrudGetAction<IPrioridad> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_PRIORIDAD,
    payload: axios.get<IPrioridad>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<IPrioridad> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_PRIORIDAD,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<IPrioridad> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_PRIORIDAD,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<IPrioridad> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_PRIORIDAD,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
